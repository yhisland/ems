/**
 * 
 */
package com.manager.model;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.manager.config.EmsConfig;
import com.manager.util.DateUtil;


/**
 * 日志
 * 
 * 
 */
public class Log extends Model<Log> {

	private static final long serialVersionUID = 1L;
	public final static String QUERY_ALL_LOGCOUNT = "select id from log_table";
	public static final Log dao = new Log();

	/**
	 * 获取表记录总数
	 * 
	 * @return int
	 */
	public int getTableCount() {
		List<Log> logList = find(Log.QUERY_ALL_LOGCOUNT);
		int num = 0;
		if (logList != null) {
			num = logList.size();
		}
		return num;
	}

	/**
	 * 获取分页日志记录
	 * 
	 * @param offset
	 * @return List<Log>
	 */
	public List<Log> findPageLogList(int offset) {
		List<Log> loglist = null;
		Page<Log> pageList = paginate((offset/EmsConfig.pageSize)+1, EmsConfig.pageSize, "select *", "from log_table");
		loglist = pageList.getList();
		if (loglist != null) {
			User user = null;
			for (Log log : loglist) {
				user = User.dao.findById(log.get("userId"));
				log.put("userInfo",
						user.get("username") + "|" + user.get("name"));
			}
		} else {
			loglist = new ArrayList<Log>();
		}
		return loglist;
	}
	
	/**
	 * 添加操作日志
	 * @param logContent:操作内容
	 * @param ip:用户ip
	 * @param userId：用户id
	 * @return boolean
	 */
	public boolean addLog(String logContent,String ip,int userId){
		Log log = new Log();
		log.set("logContent", logContent);
		log.set("ip", ip);
		log.set("userId",userId);
		log.set("logDate",DateUtil.getCurrentFormatTime(null));
		return log.save();
	}
}
