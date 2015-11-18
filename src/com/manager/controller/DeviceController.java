/**
 * 文件名：DeviceController.java
 */
package com.manager.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.manager.comm.FileUpload;
import com.manager.interceptor.LogInterceptor;
import com.manager.model.Device;
import com.manager.model.User;
import com.manager.util.ExcelUtil;


/**
 * 功能描述：设备操作控制器
 */
@Before(LogInterceptor.class)
public class DeviceController extends Controller {
	/**
	 * 添加设备信息
	 */
	public void addDevice() {
		Device device = getModel(Device.class);
		if (device.addDevice()) {
			setAttr("info", "设备添加成功!");
		} else {
			setAttr("info", "设备添加失败!");
		}
		render("/addDevice.jsp");
	}

	/**
	 * 批量添加设备
	 */
	public void addManyDevice() {
		FileUpload upload = new FileUpload(getRequest());
		byte[] bytes = (byte[]) upload.getValue("templateFile");
		List<Map<String, Object>> dataList = null;
		try {
			dataList = ExcelUtil
					.readExcel(bytes, new HashMap<String, String>());
		} catch (Exception e) {
			setAttr("info", "批量导入失败,请以本系统批量上传模板格式为准!");
		}
		if (dataList != null) {
			int failCount = 0;
			for (Map<String, Object> map : dataList) {
				if (!Device.dao.addDeviceByMany(map)) {
					failCount++;
				}
			}
			if (failCount == 0) {
				setAttr("info", "全部导入成功!");
			} else {
				setAttr("info", "有" + (dataList.size() - failCount)
						+ "条记录导入成功," + failCount + "条记录导入失败,请确保信息完整正确!");
			}
		}
		setAttr("handleUrl", "/device/addManyDevice");
		setAttr("hInfo", "批量添加设备");
		render("/addAll.jsp");
	}

	/**
	 * 查询分页设备信息
	 */
	public void queryPageDevice() {
		int offset = getParaToInt("offset");
		User user = (User)getSessionAttr("user");
		int userType = Integer.parseInt((user.get("userType")).toString());
		setAttr("num", Device.dao.getTableCount());
		setAttr("deviceList", Device.dao.findPageDeviceList(offset,userType));
		render("/showDeviceInfo.jsp");
	}

	/**
	 * 审核面跳转
	 */
	public void audit() {
		int deviceId = getParaToInt();
		Device device = Device.dao.findSignleDevice(deviceId);
		setAttr("device", device);
		render("/auditDevice.jsp");
	}

	/**
	 * 根据设备名称模糊查询
	 */
	public void queryDeviceByLike() {
		String condition = getPara("condition");
		List<Device> list = Device.dao.findLikeDeviceList(condition);
		if (list == null) {
			list = new ArrayList<Device>();
		}
		setAttr("num", list.size());
		setAttr("deviceList", list);
		render("/showDeviceInfo.jsp");
	}

	/**
	 * 删除设备
	 */
	public void deleteDevice() {
		int deviceId = getParaToInt();
		Device.dao.deleteById(deviceId);
		redirect("/device/queryPageDevice?offset=1");
	}

	/**
	 * 修改设备
	 */
	public void edit() {
		int deviceId = getParaToInt();
		Device device = Device.dao.findById(deviceId);
		setAttr("device", device);
		render("/updateDevice.jsp");
	}

	/**
	 * 将借出的设备信息更改为在库状态
	 */
	public void back() {
		int deviceId = getParaToInt();
		Device device = Device.dao.findById(deviceId);
		device.backDevice();
		redirect("/device/queryPageDevice?offset=1");
	}

	/**
	 * 更新设备
	 */
	public void updateDevice() {
		int deviceId = getParaToInt("id");
		Device device = Device.dao.findById(deviceId);
		device.setAttrs(getModel(Device.class));
		device.update();
		redirect("/device/queryPageDevice?offset=1");

	}

	/**
	 * 预约机器
	 */
	public void subscribe() {
		int deviceId = getParaToInt("deviceId");
		String subscribeNote = getPara("subscribeNote");
		Device device = Device.dao.findById(deviceId);
		User user = (User) getSession().getAttribute("user");
		device.set("currentState", 1);
		device.set("currentUserId", user.get("id"));
		device.set("subscribeNote",subscribeNote);
		device.update();
		redirect("/device/queryPageDevice?offset=1");
	}

	/**
	 * 处理审核结果
	 */
	public void completeDevice() {
		int id = getParaToInt("id");
		Device device = Device.dao.findById(id);
		Boolean result = getParaToBoolean("result");
		String returnDate = getPara("returnDate");
		if (result) {// 通过
			device.set("returnDate", returnDate);
			device.set("currentState", 2);
		} else {// 不通过
			long newId = 1L;
			User user = (User) getSession().getAttribute("user");
			if (user != null) {
				newId = user.get("id");
			}
			device.set("currentUserId", newId);
			device.set("currentState", 0);
			device.set("subscribeNote","");
		}
		device.update();
		redirect("/device/queryPageDevice?offset=1");
	}
	
	/**
	 *验证编号是否重复
	 */
	public void validateDeviceExist(){
		String equipNumber= getPara("equipNumber");
		boolean flag = false;
		if (Device.dao.findDeviceByNumber(equipNumber) == null) {
			flag = true;
		}
		reponseResult(flag);
	}
	
	/**
	 * 将结果返回给客户端
	 * @param obj
	 */
	private void reponseResult(Object obj) {
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			pw.print(obj);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/**
	 * 导出设备信息
	 */
	public void exportAllDeviceInfo() {
		List<Map<String, Object>> listInfo = Device.dao.getAllDeviceInfo();
		List<Map<String, String>> keylist = Device.dao.getKeyList();
		byte[] bytes = null;
		OutputStream out = null;
		try {
			bytes = ExcelUtil.export_excel(listInfo, keylist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// 设置相应内容的类型
			getResponse().setContentType("application/ms-excel;charset=UTF-8");
			// 设置文件保存的名称及格式后缀
			getResponse().setHeader(
					"Content-disposition",
					"attachment; filename="
							+ new String("设备信息表".getBytes("GBK"), "ISO-8859-1")
							+ ".xls");
//			getResponse().setHeader("Content-Disposition","attachment;filename=设备信息表.xls");中文文件名乱码问题
			out = getResponse().getOutputStream();
			out.write(bytes);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 删除指定id的项
	 */
	public void deleteAllSelected() {
		String[] idlist = getParaMap().get("idList[]");
		if (idlist != null) {
			for (String id : idlist) {
				Device.dao.deleteById(id);
			}
		}
		reponseResult(true);
	}
}
