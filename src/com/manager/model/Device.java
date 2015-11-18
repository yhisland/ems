/**
 * 文件名：Device.java
 */
package com.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.manager.config.EmsConfig;
import com.manager.util.RegexUtil;

/**
 * 功能描述：设备Model
 * 
 */
public class Device extends Model<Device> {
	private static final long serialVersionUID = 1L;
	public final static Device dao = new Device();
	public final static String QUERY_ALL_DEVICECOUNT = "select id from equip_table";
	public final static String QUERY_ALL_DEVICEINFO = "select * from equip_table";
	public final static String QUERY_DEVICE_NUMBER = "select * from equip_table where equipNumber=?";

	/**
	 * 获取指定设备id的维修记录
	 * 
	 * @param offset
	 * @return List<Maintain>
	 */
	public List<Device> findPageDeviceList(int offset,int userType) {
		List<Device> devicelist = null;
		List<String> sqlList = getPaginateSqlByUserType(userType);
		Page<Device> pageList = paginate((offset / EmsConfig.pageSize) + 1,
				EmsConfig.pageSize, sqlList.get(0),sqlList.get(1));
		devicelist = pageList.getList();
		changeValue(devicelist);
		return devicelist;
	}
	
	
	private List<String> getPaginateSqlByUserType(int userType){
		List<String> sqllist = new ArrayList<String>();
		if(userType==2){
			sqllist.add("select *");
			sqllist.add("from equip_table where currentSituation=0");
		}else{
			sqllist.add("select *");
			sqllist.add("from equip_table");
		}
		return sqllist;
	}

	/**
	 * 模糊查询数据
	 * 
	 * @param condition
	 * @return Device列表
	 */
	public List<Device> findLikeDeviceList(String condition) {
		List<Device> devicelist = null;
		devicelist = find("select * from equip_table where equipName LIKE '%"
				+ condition + "%'");
		changeValue(devicelist);
		return devicelist;
	}

	/**
	 * 获取单个设备转换信息
	 * @param id
	 * @return
	 */
	public Device findSignleDevice(int id) {
		List<Device> list = new ArrayList<Device>();
		list.add(findById(id));
		changeValue(list);
		return list.get(0);
	}
	
	/**
	 * 根据设备编号获取设备
	 * @param equipNumber
	 * @return Device
	 */
	public Device findDeviceByNumber(String equipNumber){
		return findFirst(Device.QUERY_DEVICE_NUMBER,equipNumber);
	}

	/**
	 * 改变设备状态标志位
	 * @param devicelist
	 */
	private void changeValue(List<Device> devicelist) {
		if (null == devicelist || devicelist.size() == 0) {
			return;
		}
		int currentUserId;
		int currentSituation;
		int currentState;
		for (Device device : devicelist) {
			currentUserId = Integer.parseInt(device.get("currentUserId")
					.toString());
			currentSituation = Integer.parseInt(device.get("currentSituation")
					.toString());
			currentState = Integer.parseInt(device.get("currentState")
					.toString());
			if (currentUserId == -1) {
				device.put("currentUser", "无");
			} else {
				User user = User.dao.findById(currentUserId);
				if (user != null) {
					device.put("currentUser", user.get("username").toString()
							+ "|" + user.get("name").toString());
				} else {
					device.put("currentUser", "无");
				}
			}
			// 当前设备使用情况0:正常，1:损坏
			if (currentSituation == 0) {
				device.set("currentSituation", "正常");
			} else {
				device.set("currentSituation", "损坏");
			}
			// 当前设备状态0:在库，1:预约，2:借出
			if (currentState == 0) {
				device.set("currentState", "在库");
			} else if (currentState == 1) {
				device.set("currentState", "预约");
			} else {
				device.set("currentState", "借出");
			}
		}
	}

	/**
	 * 获取表记录总数
	 * 
	 * @return
	 */
	public int getTableCount() {
		List<Device> deviceAllList = find(Device.QUERY_ALL_DEVICECOUNT);
		int num = 0;
		if (deviceAllList != null) {
			num = deviceAllList.size();
		}
		return num;
	}

	/**
	 * 获取待审核的设备申请总数
	 * 
	 * @return
	 */
	public int getTableYuYueCount() {
		List<Device> list = find("select id from equip_table where currentState = '1'");
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

	/**
	 * 新增设备信息
	 */
	public boolean addDevice() {
		this.set("currentUserId", 1);
		this.set("currentSituation", 0);
		this.set("currentState", 0);
		this.set("returnDate", "");
		this.set("subscribeNote","");
		return this.save();
	}

	/**
	 * 归还设备时，还原借用字段信息
	 * 
	 * @return boolean
	 */
	public boolean backDevice() {
		this.set("currentUserId", 1);
		this.set("currentState", 0);
		this.set("returnDate", "");
		this.set("subscribeNote","");
		return this.update();
	}

	/**
	 * 批量添加设备
	 * @param map
	 * @return boolean
	 */
	public boolean addDeviceByMany(Map<String, Object> map) {
		if (map == null) {
			return false;
		} else {
			String equipNumber = map.get("编号").toString();
			String price = map.get("单价").toString();
			if(findDeviceByNumber(equipNumber)!=null||!RegexUtil.regexPrice(price)){
				return false;
			}
			Device device = new Device();
			device.set("equipNumber",equipNumber);
			device.set("equipName",map.get("名称"));
			device.set("equipModel",map.get("型号"));
			device.set("price",price);
			device.set("buyDate",map.get("购买时间"));
			return device.addDevice();
		}
	}
	
	/**
	 * 获取所有设备信息
	 * @return
	 */
	public List<Map<String,Object>> getAllDeviceInfo(){
		List<Device> devicelist = find(Device.QUERY_ALL_DEVICEINFO);
		List<Map<String,Object>> list = null;
		changeValue(devicelist);
		if(devicelist!=null){
			list = new ArrayList<Map<String,Object>>();
			for(Device device:devicelist){
				list.add(device.getAttrs());
			}
		}
		return list;
	}
	
	/**
	 * 获取表头数据的头部信息
	 * @return List<Map<String,String>>
	 */
	public List<Map<String,String>> getKeyList(){
		List<Map<String,String>> keylist = new ArrayList<Map<String,String>>();
		Map<String,String>map = new HashMap<String,String>();
		map.put("headText", "编号");map.put("dataField", "equipNumber");
		keylist.add(map);
		map = new HashMap<String,String>();
		map.put("headText", "名称");map.put("dataField", "equipName");
		keylist.add(map);
		map = new HashMap<String,String>();
		map.put("headText", "型号");map.put("dataField", "equipModel");
		keylist.add(map);
		map = new HashMap<String,String>();
		map.put("headText", "单价");map.put("dataField", "price");
		keylist.add(map);
		map = new HashMap<String,String>();
		map.put("headText", "购买时间");map.put("dataField", "buyDate");
		keylist.add(map);
		map = new HashMap<String,String>();
		map.put("headText", "使用情况");map.put("dataField", "currentSituation");
		keylist.add(map);
		map = new HashMap<String,String>();
		map.put("headText", "当前状态");map.put("dataField", "currentState");
		keylist.add(map);
		map = new HashMap<String,String>();
		map.put("headText", "当前使用人");map.put("dataField", "currentUser");
		keylist.add(map);
		map = new HashMap<String,String>();
		map.put("headText", "归还时间");map.put("dataField", "returnDate");
		keylist.add(map);
		return keylist;
	}
	
}
