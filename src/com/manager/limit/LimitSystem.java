/**
 * 
 */
package com.manager.limit;

import java.util.HashSet;
import java.util.Set;

/**
 * 菜单权限系统
 * 
 */
public final class LimitSystem {
	
	public final static LimitSystem limitSystem = getInstance();
	
	/**教师和学生不具备的权限集*/
	private Set<String> noLimit = null;
	
	private static LimitSystem getInstance(){
		LimitSystem limitSys = null;
	    synchronized(LimitSystem.class) {
	    	if(limitSystem==null){
	    		limitSys = new LimitSystem();
	    	}else{
	    		limitSys = limitSystem;
	    	}
		}
		return limitSys;
	}
	
	private LimitSystem(){
		noLimit = new HashSet<String>();
		init();
	}
	/**
	 *左侧菜单权限系统初始化
	 *可以将权限放在xml配置文件中进行配置
	 */
	public void init(){
		System.out.println("正在配置菜单权限系统...");
		noLimit.add("/user/queryPageUser");
		noLimit.add("/addUserJsp");
		noLimit.add("/addDeviceJsp");
		noLimit.add("/addManyUserJsp");
		noLimit.add("/addManyDeviceJsp");
		noLimit.add("/maintain/queryAllDevice");
		noLimit.add("/log/queryPageLog");
		noLimit.add("/device/exportAllDeviceInfo");
		System.out.println("菜单权限系统配置完毕！");
	}
	
	/**
	 * 判断当前用户是否拥有此权限
	 * @param userType
	 * @param menuUrl
	 * @return boolean
	 */
	public boolean ishaveLimit(int userType,String menuUrl){
		if(userType==0){//管理员
			return true;
		}else{//教师，学生
			if(noLimit.contains(menuUrl)){
				return false;
			}else{
				return true;
			}
		}
	}
}
