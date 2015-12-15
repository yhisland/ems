/**
* 文件名：IndexController.java
*/
package com.manager.controller;

import com.jfinal.core.Controller;

/**
 * 静态资源动作转发器，便于拦截器拦截，主要是便于控制主界面静态资源访问的权限
 * 之所以要通过Action动作来间接跳转到静态资源页面(例如:jsp文件,html文件)
 * 是因为JFinal中ActionHandler处理器环境下定义的拦截器和验证器等仅能拦截具体Action动作的请求。
 */
public class StaticResourceController extends Controller
{
	/**
	 * 跳转到登录页
	 */
    public void index()
    {
        render("login.jsp");
    }
    
    /**
     * 跳转到修改密码界面
     */
    public void updatepwJsp(){
    	render("/index.html");
    }
    
    /**
     * 跳转到展示用户信息界面
     */
    public void showinfoJsp(){
    	render("/86_analysis.html");
//    	render("/showinfo.jsp");    	
    }
    
    /**
     * 跳转到新增用户界面
     */
    public void addUserJsp(){
    	render("/86Test.html");
//    	render("/addUser.jsp");
    }
    
    
    /**
     * 跳转到新增设备界面
     */
    public void addDeviceJsp(){
    	render("/addDevice.jsp");
    }
    
    /**
     * 批量添加用户
     */
    public void addManyUserJsp(){
    	setAttr("handleUrl", "/user/addManyUser");
    	setAttr("hInfo", "批量添加用户");
    	render("/addAll.jsp");
    }
    
    /**
     * 批量添加设备
     */
    public void addManyDeviceJsp(){
    	setAttr("handleUrl", "/device/addManyDevice");
    	setAttr("hInfo", "批量添加设备");
    	render("/addAll.jsp");
    }
    /**
     * 批量添加投诉信息
     */
    public void addManyComplainJsp(){
    	setAttr("handleUrl", "/complain/addManyComplain");
    	setAttr("hInfo", "批量添加投诉");
    	render("/addAll.jsp");
    }    
}
