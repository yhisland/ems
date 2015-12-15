/**
 * 
 */
package com.manager.comm;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 */
public final class UrlToLogInfo {
   private Map<String,String> map = null;
   public static final UrlToLogInfo urlMap = new UrlToLogInfo();
   
   private UrlToLogInfo(){
	   map = new HashMap<String,String>();
	   //用户操作模块
	   map.put("/user/login", "登录系统");
	   map.put("/user/outFromSystem", "退出系统");
	   map.put("/user/addUser", "添加用户信息");
	   map.put("/user/addManyUser", "批量添加用户");
	   map.put("/user/updateUserByAdmin","管理员修改用户信息");
	   map.put("/user/deleteUser","删除用户信息");
	   map.put("/user/updatePassword","用户修改密码");
	   map.put("/user/updateUserInfo","用户修改个人资料");
	   //设备操作模块
	   map.put("/device/addDevice", "管理员添加设备");
	   map.put("/device/addManyDevice", "批量添加设备");
	   map.put("/complain/addManyComplain", "批量添加投诉");
	   map.put("/device/exportAllDeviceInfo", "导出设备信息");
	   map.put("/device/deleteDevice", "管理员删除设备");
	   map.put("/device/updateDevice", "更新设备信息");
	   map.put("/device/subscribe","用户预约设备");
	   map.put("/device/completeDevice","管理员审核预约申请");
	   //维修记录操作模块
	   map.put("/maintain/addMaintain", "管理员添加维修记录");
	   map.put("/maintain/deleteMaintain", "管理员删除维修记录");
   }
   
   /**
    * 获取对应路径的操作内容
    * @param actionUrl
    * @return String
    */
   public String getLogContent(String actionUrl){
	   return map.get(actionUrl);
   }
}
