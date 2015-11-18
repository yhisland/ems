/**
 * 文件名：User.java
 * 创建日期： 2014年5月7日
 * 作者：     lipanpan
 * Copyright (c) 2009-2011 无线开发室
 * All rights reserved.
 
 * 修改记录：
 * 	1.修改时间：2014年5月7日
 *   修改人：lipanpan
 *   修改内容：
 */
package com.manager.model;

import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.manager.config.EmsConfig;
import com.manager.util.MD5;
import com.manager.util.RegexUtil;


/**
 * 功能描述：
 * 
 */
public class User extends Model<User> {
	private static final long serialVersionUID = 1L;

	public final static User dao = new User();

	/** 根据用户名username查询用户User */
	public final static String QUERY_USER_BYNAME = "select * from user_table where username=?";
	/** 查询所有用户User信息 */
	public final static String QUERY_ALL_USER = "select * from user_table";

	/**
	 * 获取分页数据，并将userType转化为字符串类型
	 * 
	 * @param offset
	 * @return
	 */
	public List<User> findPageUserList(int offset) {
		List<User> userlist = null;
		Page<User> pageList = paginate((offset / EmsConfig.pageSize) + 1,
				EmsConfig.pageSize, "select *", "from user_table");
		userlist = pageList.getList();
		for (User user : userlist) {
			changeStatue(user);
		}
		return userlist;
	}

	/**
	 * 获取表记录总条数
	 * 
	 * @return
	 */
	public int getTableCount() {
		List<User> userAllList = find(User.QUERY_ALL_USER);
		int allUserNum = 0;
		if (userAllList != null) {
			allUserNum = userAllList.size();
		}
		return allUserNum;
	}

	/**
	 * 根据用户名查询用户信息
	 * 
	 * @return User
	 */
	public User getUserByUserName(String username) {
		User user = findFirst(User.QUERY_USER_BYNAME, username);
		changeStatue(user);
		return user;
	}

	/**
	 * 改变用户类型标志位
	 * 
	 * @param user
	 */
	private void changeStatue(User user) {
		if (user != null) {
			int type = user.get("userType");
			String userTypeText = "";
			if (type == 0) {
				userTypeText = "管理员";
			} else if (type == 1) {
				userTypeText = "教师";
			} else {
				userTypeText = "学生";
			}
			user.put("userTypeText", userTypeText);
		}
	}

	/**
	 * 批量添加用户
	 * @param map
	 * @return boolean
	 */
	public boolean addUserByMany(Map<String, Object> map) {
		if (map == null) {
			return false;
		} else {
			int userType;
			Object userTypeText = map.get("用户类型");
			if ("管理员".equals(userTypeText)) {
				userType = 0;
			} else if ("教师".equals(userTypeText)) {
				userType = 1;
			} else {
				userType = 2;
			}
			String username = map.get("用户名").toString();
			String telNumber = map.get("联系电话").toString();
			if(getUserByUserName(username)!=null||!RegexUtil.regexTelNumber(telNumber)){
				return false;
			}
			User user = new User();
			user.set("username", username);
			user.set("password", MD5.getMD5(map.get("密码").toString()));
			user.set("name", map.get("真实姓名"));
			user.set("userType", userType);
			user.set("telNumber",telNumber);
			return user.save();
		}
	}
}
