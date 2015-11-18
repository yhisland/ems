/**
 * 文件名：UserController.java
 */
package com.manager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.manager.comm.FileUpload;
import com.manager.interceptor.LogInterceptor;
import com.manager.model.Device;
import com.manager.model.User;
import com.manager.util.ExcelUtil;
import com.manager.util.MD5;


/**
 * 功能描述：用户操作控制器
 */
@Before(LogInterceptor.class)
public class UserController extends Controller {
	/**
	 * 处理用户登录Action
	 */
	public void login() {
		String username = getPara("username");
		String password = MD5.getMD5(getPara("password"));
		User user = User.dao.getUserByUserName(username);
		if (user == null) {
			getRequest().setAttribute("errorMsg", "用户名不存在！");
			render("/login.jsp");
		} else {
			if (user.get("password").equals(password)) {
				getSession().setAttribute("user", user);
				render("/main.jsp");
			} else {
				getRequest().setAttribute("errorMsg", "用户名或密码错误！");
				render("/login.jsp");
			}
		}
	}

	/**
	 * 退出系统
	 */
	public void outFromSystem() {
		HttpSession session = getSession();
		if (session != null) {
			session.setAttribute("user", null);
		}
		redirect("/login.jsp");
	}

	/**
	 * 查询分页用户
	 */
	public void queryPageUser() {
		int offset = getParaToInt("offset");
		setAttr("num", User.dao.getTableCount());
		setAttr("userList", User.dao.findPageUserList(offset));
		render("showUserList.jsp");
	}

	/**
	 * 新增用户
	 */
	public void addUser() {
		User user = getModel(User.class);
		user.set("password", MD5.getMD5(user.get("password").toString()));
		if (user.save()) {
			setAttr("info", "新增用户成功！");
		} else {
			setAttr("info", "新增用户失败！");
		}
		render("/addUser.jsp");
	}

	/**
	 * 批量添加用户
	 */
	public void addManyUser() {
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
				if (!User.dao.addUserByMany(map)) {
					failCount++;
				}
			}
			if(failCount==0){
				setAttr("info", "全部导入成功!");
			}else{
				setAttr("info", "有"+(dataList.size()-failCount)+"条记录导入成功,"+failCount+"条记录导入失败,请确保信息完整正确!");
			}
		}
		setAttr("handleUrl", "/user/addManyUser");
    	setAttr("hInfo", "批量添加用户");
    	render("/addAll.jsp");
	}

	/**
	 * 仅供admin调用接口，修改用户信息
	 */
	public void editUser() {
		int userId = getParaToInt();
		User user = User.dao.findById(userId);
		setAttr("user", user);
		render("/updateUser.jsp");
	}

	/**
	 * admin修改用户信息的接口
	 */
	public void updateUserByAdmin() {
		int userId = getParaToInt("id");
		User user = User.dao.findById(userId);
		User userInfo = getModel(User.class);
		if (user != null) {
			user.setAttrs(userInfo);
			user.update();
		}
		redirect("/user/queryPageUser?offset=1");
	}

	/**
	 * 删除用户信息
	 */
	public void deleteUser() {
		int userId = getParaToInt();
		User.dao.deleteById(userId);
		redirect("/user/queryPageUser?offset=1");
	}

	/**
	 * 修改用户密码
	 */
	public void updatePassword() {
		User user = getSessionAttr("user");
		String newPassword = MD5.getMD5(getPara("newPassword1"));
		user.set("password", newPassword);
		if (user.update()) {
			setAttr("info", "用户密码修改成功！");
		} else {
			setAttr("info", "用户密码修改失败！");
		}
		render("/updatepw.jsp");
	}

	/**
	 * 用户修改用户资料信息真实姓名，联系电话等
	 */
	public void updateUserInfo() {
		User user = getSessionAttr("user");
		user.set("name", getPara("name"));
		user.set("telNumber", getPara("telNumber"));
		if (user.update()) {
			setAttr("info", "用户信息修改成功！");
		} else {
			setAttr("info", "用户信息修改失败！");
		}
		render("/showinfo.jsp");
	}

	/**
	 * 验证用户名是否存在
	 */
	public void validateUserName() {
		String username = getPara("username");
		boolean flag = false;
		if (User.dao.getUserByUserName(username) == null) {
			flag = true;
		}
		reponseResult(flag);
	}

	/**
	 * 验证原始密码正确
	 */
	public void valiatePassword() {
		User user = getSessionAttr("user");
		String password = MD5.getMD5(getPara("password"));
		boolean flag = false;
		if (user != null && user.get("password").equals(password)) {
			flag = true;
		}
		reponseResult(flag);
	}

	/**
	 * 仅供管理员获取待处理的审核流程个数
	 */
	public void getTask() {
		int count = Device.dao.getTableYuYueCount();
		reponseResult(count);
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
	 * 删除指定id的项
	 */
	public void deleteAllSelected() {
		String[] idlist = getParaMap().get("idList[]");
		if (idlist != null) {
			for (String id : idlist) {
				User.dao.deleteById(id);
			}
		}
		reponseResult(true);
	}
}
