package com.manager.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
import com.jfinal.kit.JsonKit;
import com.manager.comm.FileUpload;
import com.manager.interceptor.LogInterceptor;
import com.manager.model.Complain;
import com.manager.model.User;
import com.manager.util.ExcelUtil;

  

/**   
 * 文件名：ComplainController.java 
 * 描述：投诉控制器
 * 创建人：Administrator
 * 创建时间： 2015-11-4下午6:20:49
 * 修改人：Administrator   
 * 修改时间：2015-11-4下午6:20:49 
 * 修改备注：        
 *    
 */ 
@Before(LogInterceptor.class)
@ControllerBind(controllerKey ="complain_table")
public class ComplainController extends Controller{
//	String type=null;
	
	/**
	 * 添加投诉信息
	 */
	public void addComplain() {
		Complain complain = getModel(Complain.class);
		if (complain.addComplain()) {
			setAttr("info", "投诉添加成功!");
		} else {
			setAttr("info", "投诉添加失败!");
		}
		render("/addComplain.jsp");
	}

	/**
	 * 查找所有
	 */		
	public void selectAll(){
		System.out.println("进入selectAll**************************************************");
		String str="{}";
		JSONObject obj = new JSONObject();
	    obj=JSONObject.parseObject(getPara("where",str));
		int page=getParaToInt("page",1);
		int pagesize=getParaToInt("pagesize",20);
		String sortname = getPara("sortname");
		String sortorder = getPara("sortorder");
		Map<String, Object> map =Complain.dao.selectAll(page, pagesize, obj,sortname,sortorder);
		System.out.println("**************************************************selectAll");
		renderJson(map);
	}	
	
	/**
	 * 显示图表
	 */	
	public void getList(){
		System.out.println("进入getList**************************************************");
		String str="{}";
//		String wh = getPara("where",str);
		JSONObject obj = new JSONObject();
//		System.out.println("wh="+wh);
//		JSONObject jobj=JSON.parseObject(getPara("where",str));
	    obj=JSONObject.parseObject(getPara("where",str));
//		obj=JSONObject.parseObject(wh);
	    System.out.println("obj="+obj);
		int page=getParaToInt("page",1);
		int pagesize=getParaToInt("pagesize",20);
		String sortname = getPara("sortname");
		String sortorder = getPara("sortorder");
		List<Complain> list	 = Complain.dao.getList(page, pagesize, obj,sortname,sortorder);
		Object data = JsonKit.listToJson(list, 3);
//		type=(String) data;
//		setAttr("type", getPara("type",""));
		System.out.println("data="+data);
		renderJson(data);
	}	
	
	/**
	 * 批量添加投诉
	 */
	public void addManyComplain() {
		System.out.println("进入批量添加投诉addManyComplain***********************************");
		FileUpload upload = new FileUpload(getRequest());
		byte[] bytes = (byte[]) upload.getValue("templateFile");
		List<Map<String, Object>> dataList = null;
		try {
			dataList = ExcelUtil
					.readExcel(bytes, new HashMap<String, String>());
		} catch (Exception e) {
			setAttr("info", "批量导入失败,请以本系统批量上传模板格式为准!");
		}
		System.out.println("进入批量添加投诉addManyComplain="+dataList);
		if (dataList != null) {
			int failCount = 0;
			for (Map<String, Object> map : dataList) {
				if (!Complain.dao.addComplainByMany(map)) {
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
		setAttr("handleUrl", "/complain/addManyComplain");
		setAttr("hInfo", "批量添加投诉");
		render("/addAll.jsp");
	}

	/**
	 * 查询分页投诉信息
	 */
	public void queryPageComplain() {
		System.out.println("进入queryPageComplain**************************************************");
		int offset = getParaToInt("offset");
		User user = (User)getSessionAttr("user");
		int userType = Integer.parseInt((user.get("userType")).toString());
		setAttr("num", Complain.dao.getTableCount());
		setAttr("complainList", Complain.dao.findPageComplainList(offset,userType));
		render("/showComplainInfo.jsp");
		
/*		int offset = getParaToInt("offset");
		User user = (User)getSessionAttr("user");
		int userType = Integer.parseInt((user.get("userType")).toString());
		setAttr("num", Device.dao.getTableCount());
		setAttr("deviceList", Device.dao.findPageDeviceList(offset,userType));
		render("/showDeviceInfo.jsp");*/
	}

	/**
	 * 审核面跳转
	 */
	public void audit() {
		System.out.println("进入audit**************************************************");
		int complainId = getParaToInt();
		Complain complain = Complain.dao.findSignleComplain(complainId);
		setAttr("complain", complain);
		render("/auditComplain.jsp");
	}

	/**
	 * 根据宽带地址模糊查询
	 */
	public void queryComplainByLike() {
		System.out.println("进入queryComplainByLike**************************************************");
		String condition = getPara("condition");
		List<Complain> list = Complain.dao.findLikeComplainList(condition);
		if (list == null) {
			list = new ArrayList<Complain>();
		}
		setAttr("num", list.size());
		setAttr("complainList", list);
		render("/showComplainInfo.jsp");
	}

	/**
	 * 删除投诉
	 */
/*	public void deleteComplain() {
		int complainId = getParaToInt();
		Complain.dao.deleteById(complainId);
		redirect("/complain/queryPageComplain?offset=1");
	}*/

	/**
	 * 修改投诉
	 */
	public void edit() {
		System.out.println("进入edit**************************************************");
		int complainId = getParaToInt();
		Complain complain = Complain.dao.findById(complainId);
		setAttr("complain", complain);
		render("/updateComplain.jsp");
	}

	/**
	 * 将借出的投诉信息更改为在库状态
	 */
	public void back() {
		System.out.println("进入back**************************************************");
		int complainId = getParaToInt();
		Complain complain = Complain.dao.findById(complainId);
		complain.backComplain();
		redirect("/complain/queryPageComplain?offset=1");
	}

	/**
	 * 更新投诉
	 */
	public void updateComplain() {
		System.out.println("进入updateComplain**************************************************");
		int complainId = getParaToInt("id");
		Complain complain = Complain.dao.findById(complainId);
		complain.setAttrs(getModel(Complain.class));
		complain.update();
		redirect("/complain/queryPageComplain?offset=1");

	}

	/**
	 * 预约机器
	 */
	public void subscribe() {
		System.out.println("进入subscribe**************************************************");
		int complainId = getParaToInt("complainId");
		String subscribeNote = getPara("subscribeNote");
		Complain complain = Complain.dao.findById(complainId);
		User user = (User) getSession().getAttribute("user");
		complain.set("currentState", 1);
		complain.set("currentUserId", user.get("id"));
		complain.set("subscribeNote",subscribeNote);
		complain.update();
		redirect("/complain/queryPageComplain?offset=1");
	}

	/**
	 * 处理审核结果
	 */
	public void completeComplain() {
		System.out.println("进入completeComplain**************************************************");
		int id = getParaToInt("id");
		Complain complain = Complain.dao.findById(id);
		Boolean result = getParaToBoolean("result");
		String returnDate = getPara("returnDate");
		if (result) {// 通过
			complain.set("returnDate", returnDate);
			complain.set("currentState", 2);
		} else {// 不通过
			long newId = 1L;
			User user = (User) getSession().getAttribute("user");
			if (user != null) {
				newId = user.get("id");
			}
			complain.set("currentUserId", newId);
			complain.set("currentState", 0);
			complain.set("subscribeNote","");
		}
		complain.update();
		redirect("/complain/queryPageComplain?offset=1");
	}
	
	/**
	 *验证编号是否重复
	 */
	public void validateComplainExist(){
		System.out.println("进入validateComplainExist**************************************************");
		String equipNumber= getPara("equipNumber");
		boolean flag = false;
		if (Complain.dao.findComplainByNumber(equipNumber) == null) {
			flag = true;
		}
		reponseResult(flag);
	}
	
	/**
	 * 将结果返回给客户端
	 * @param obj
	 */
	private void reponseResult(Object obj) {
		System.out.println("进入reponseResult**************************************************");
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
	 * 导出投诉信息
	 */
	public void exportAllComplainInfo() {
		System.out.println("进入exportAllComplainInfo**************************************************");
		List<Map<String, Object>> listInfo = Complain.dao.getAllComplainInfo();
		List<Map<String, String>> keylist = Complain.dao.getKeyList();
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
							+ new String("投诉信息表".getBytes("GBK"), "ISO-8859-1")
							+ ".xls");
//			getResponse().setHeader("Content-Disposition","attachment;filename=投诉信息表.xls");中文文件名乱码问题
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
		System.out.println("进入deleteAllSelected**************************************************");
		String[] idlist = getParaMap().get("idList[]");
		if (idlist != null) {
			for (String id : idlist) {
				Complain.dao.deleteById(id);
			}
		}
		reponseResult(true);
	}
}
