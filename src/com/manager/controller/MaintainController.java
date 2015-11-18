/**
* 文件名：MaintainController.java
*/
package com.manager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.manager.interceptor.LogInterceptor;
import com.manager.model.Device;
import com.manager.model.Maintain;

/**
 * 功能描述：维修记录操作控制器
 */
@Before(LogInterceptor.class)
public class MaintainController extends Controller
{
    
    /**
     * 新增维修记录
     */
    public void addMaintain(){
        boolean flag = getModel(Maintain.class).save();
        if (flag)
        {
            setAttr("info", "新增维修记录成功!");
        }
        else
        {
            setAttr("info", "新增维修记录成功失败!");
        }
        queryAllDevice();
    }
    
    /**
     * 删除维修记录
     */
    public void deleteMaintain(){
        int deviceId = getParaToInt(0);
        int maintainId = getParaToInt(1);
        Maintain.dao.deleteById(maintainId);
        redirect("/maintain/queryPageMaintain/"+deviceId+"-1");
    }
    
    /**
     * 查询维修记录
     */
    public void queryPageMaintain(){
        int deviceId = getParaToInt(0);
        int offset = getParaToInt(1);
        setAttr("deviceId",deviceId);
        setAttr("num", Maintain.dao.getTableCount());
        setAttr("maintainList",Maintain.dao.findPageMaintainList(offset, deviceId));
        render("/showMaintainInfo.jsp");      
    }
    
    /**
     * 获取所有的设备列表
     */
    public void queryAllDevice(){
    	List<Device> list =Device.dao.find("select id,equipNumber from equip_table");
        setAttr("devices", list);
    	render("/addMaintain.jsp");
    }
    
    /**
	 * 删除指定id的项
	 */
	public void deleteAllSelected() {
		String[] idlist = getParaMap().get("idList[]");
		if (idlist != null) {
			for (String id : idlist) {
				Maintain.dao.deleteById(id);
			}
		}
		PrintWriter pw = null;
		try {
			pw = getResponse().getWriter();
			pw.write("true");
			pw.flush();
		} catch (IOException e) {
			System.err.println("操作异常:"+e.getMessage());
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
}
