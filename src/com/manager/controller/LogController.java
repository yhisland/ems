/**
 * 
 */
package com.manager.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.jfinal.core.Controller;
import com.manager.model.Log;


/**
 * 日志操作控制器
 * 
 */
public class LogController extends Controller {

	/**
	 * 删除日志
	 */
	public void deleteLog() {
		int logId = getParaToInt();
		Log.dao.deleteById(logId);
		redirect("/log/queryPageLog?offset=1");
	}

	/**
	 * 查看日志分页信息
	 */
	public void queryPageLog() {
		int offset = getParaToInt("offset");
		setAttr("num", Log.dao.getTableCount());
		setAttr("logList", Log.dao.findPageLogList(offset));
		render("/showLogList.jsp");
	}

	/**
	 * 删除指定id的项
	 */
	public void deleteAllSelected() {
		String[] idlist = getParaMap().get("idList[]");
		if (idlist != null) {
			for (String id : idlist) {
				Log.dao.deleteById(id);
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
