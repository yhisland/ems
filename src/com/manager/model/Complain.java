package com.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.manager.config.EmsConfig;
import com.manager.util.RegexUtil;

/**   
 * 文件名：Complain.java 
 * 描述：投诉记录Model
 * 创建人：Administrator
 * 创建时间： 2015-11-4下午6:30:09
 * 修改人：Administrator   
 * 修改时间：2015-11-4下午6:30:09 
 * 修改备注：        
 *    
 */ 
public class Complain extends Model<Complain> {
	private static final long serialVersionUID = 1L;
	public final static Complain dao = new Complain();
	public final static String QUERY_ALL_COMPLAINCOUNT = "select id from complain_table";
	public final static String QUERY_ALL_COMPLAININFO = "select * from complain_table";
	public final static String QUERY_COMPLAIN_NUMBER = "select * from complain_table where complainPhone=?";

	/**
	 * 获取指定投诉id的维修记录
	 * 
	 * @param offset
	 * @return List<Maintain>
	 */
	public List<Complain> findPageComplainList(int offset,int userType) {
		List<Complain> complainlist = null;
		List<String> sqlList = getPaginateSqlByUserType(userType);
		Page<Complain> pageList = paginate((offset / EmsConfig.pageSize) + 1,
				EmsConfig.pageSize, sqlList.get(0),sqlList.get(1));
		complainlist = pageList.getList();
		changeValue(complainlist);
		return complainlist;
	}

	/**
	 * 显示图表
	 */		
	
	public List<Complain> getList(int page,int pagesize,JSONObject jObject,String sortname,String sortorder){
		List<Complain> list = new ArrayList<Complain>();
		String select="select * "
						 /*+"       creatdate," 
						 +"       round(dsr_qual_score / dsr_qual_comt_cnt, 4) as dsr_qual," 
						 +"       round(dsr_deli_score / dsr_deli_comt_cnt, 4) as dsr_deli," 
						 +"       round(dsr_serv_score / dsr_serv_comt_cnt, 4) as dsr_serv," 
						 +"       round(dsr_logi_score / dsr_logi_comt_cnt, 4) as dsr_logi "*/;
		
		String sqlExceptSelect=" from complain_table "
						 +" where 1 = 1 ";

		StringBuffer  sb=new StringBuffer();
		sb.append(sqlExceptSelect);
/*		//查找
		//按周或月
		if (StringKit.notBlank(jObject.getString("DATE"))) {
			if (jObject.getString("DATE").length()<=7) {
				String date3   = jObject.getString("DATE");
				sb.append("  and  substr(to_char(creatdate,'yyyy-mm-dd'),1,7) = '"+date3+"' ");
			}else if(jObject.getString("DATE").length()>10) {
				String date   = jObject.getString("DATE").substring(0,10);
				String date2  =jObject.getString("DATE").substring(11,21); 
				sb.append("  and  to_char(creatdate,'yyyy-mm-dd') <= '"+date2+"' ");
				sb.append("  and  to_char(creatdate,'yyyy-mm-dd') >= '"+date+"' ");
			}
		}		
		//自定义时间查询
		//开始时间
		if (StringKit.notBlank(jObject.getString("DATE1"))) {
			String DATE1= jObject.getString("DATE1");
			DATE1=DATE1.substring(0,10);
			sb.append(" and to_char(creatdate,'yyyy-mm-dd') >='"+DATE1+"' ");
		}
		//结束时间
		if (StringKit.notBlank(jObject.getString("DATE2"))) {
			String DATE2= jObject.getString("DATE2");
			DATE2=DATE2.substring(0,10);
			sb.append(" and to_char(creatdate,'yyyy-mm-dd') <='"+DATE2+"' ");
		}				
		//店铺
		if (StringKit.notBlank(jObject.getString("SHOP_HEAD_ID"))) {
			String shop_head_id= jObject.getString("SHOP_HEAD_ID");
			sb.append(" and shop_head.shop_head_id = '"+shop_head_id+"'" );
		}		
		sb.append(" order by creatdate ");*/
		list = Complain.dao.find(select+sb.toString());
		System.out.println("list="+list);
		return  list;		
	}	
	
	
	
	
	
	private List<String> getPaginateSqlByUserType(int userType){
		List<String> sqllist = new ArrayList<String>();
		if(userType==2){
			sqllist.add("select *");
			sqllist.add("from complain_table where currentStatus <> 2 ");////// where currentSituation=0
		}else{
			sqllist.add("select *");
			sqllist.add("from complain_table");
		}
		System.out.println(sqllist);
		return sqllist;
	}

	/**
	 * 根据宽带地址模糊查询数据
	 * 
	 * @param condition
	 * @return Complain列表
	 */
	public List<Complain> findLikeComplainList(String condition) {
		List<Complain> complainlist = null;
		complainlist = find("select * from complain_table where site LIKE '%"
				+ condition + "%'");
		changeValue(complainlist);
		return complainlist;
	}

	/**
	 * 获取单个投诉转换信息
	 * @param id
	 * @return
	 */
	public Complain findSignleComplain(int id) {
		List<Complain> list = new ArrayList<Complain>();
		list.add(findById(id));
		changeValue(list);
		return list.get(0);
	}
	
	/**
	 * 根据宽带投诉号码获取投诉
	 * @param complainPhone
	 * @return Complain
	 */
	public Complain findComplainByNumber(String complainPhone){
		return findFirst(Complain.QUERY_COMPLAIN_NUMBER,complainPhone);
	}

	/**
	 * 改变投诉状态标志位
	 * @param complainlist
	 */
	private void changeValue(List<Complain> complainlist) {
		if (null == complainlist || complainlist.size() == 0) {
			return;
		}
//		int currentUserId;
		int currentStatus;
		int urgencyStatus;
		for (Complain complain : complainlist) {
//			currentUserId = Integer.parseInt(complain.get("currentUserId")
//					.toString());
			currentStatus = Integer.parseInt(complain.get("currentStatus")
					.toString());
			urgencyStatus = Integer.parseInt(complain.get("urgencyStatus")
					.toString());
/*			if (currentUserId == -1) {
				complain.put("currentUser", "无");
			} else {
				User user = User.dao.findById(currentUserId);
				if (user != null) {
					complain.put("currentUser", user.get("username").toString()
							+ "|" + user.get("name").toString());
				} else {
					complain.put("currentUser", "无");
				}
			}*/
			// 当前工单状态0:运行，1:完成，2删除
			if (currentStatus == 0) {
				complain.set("currentStatus", "运行");
			} else if (currentStatus == 1) {
				complain.set("currentStatus", "完成");
			} else {
				complain.set("currentStatus", "删除");
			}
			// 当前投诉紧急程度0:一般，1:紧急，2:非常紧急
			if (urgencyStatus == 0) {
				complain.set("urgencyStatus", "一般");
			} else if (urgencyStatus == 1) {
				complain.set("urgencyStatus", "紧急");
			} else {
				complain.set("urgencyStatus", "非常紧急");
			}
		}
	}

	/**
	 * 获取表记录总数
	 * 
	 * @return
	 */
	public int getTableCount() {
		List<Complain> complainAllList = find(Complain.QUERY_ALL_COMPLAINCOUNT);
		int num = 0;
		if (complainAllList != null) {
			num = complainAllList.size();
		}
		return num;
	}

	/**
	 * 获取待运行投诉工单总数
	 * 
	 * @return
	 */
	public int getTableYuYueCount() {
		List<Complain> list = find("select id from complain_table where currentState = '0'");
		if (list != null) {
			return list.size();
		} else {
			return 0;
		}
	}

	/**
	 * 新增投诉信息
	 */
	public boolean addComplain() {
		this.set("currentStatus", 0);
		this.set("urgencyStatus", 0);
//		this.set("returnDate", "");
//		this.set("subscribeNote","");
		return this.save();
	}

	/**
	 * 处理完投诉时，还原处理状态字段信息
	 * 
	 * @return boolean
	 */
	public boolean backComplain() {
		this.set("currentStatus", 1);
		this.set("urgencyStatus", 0);
//		this.set("returnDate", "");
//		this.set("subscribeNote","");
		return this.update();
	}

	/**
	 * 批量添加投诉
	 * @param map
	 * @return boolean
	 */
	public boolean addComplainByMany(Map<String, Object> map) {
		if (map == null) {
			return false;
		} else {
			String equipNumber = map.get("编号").toString();
			String price = map.get("单价").toString();
			if(findComplainByNumber(equipNumber)!=null||!RegexUtil.regexPrice(price)){
				return false;
			}
			Complain complain = new Complain();
			complain.set("equipNumber",equipNumber);
			complain.set("equipName",map.get("名称"));
			complain.set("equipModel",map.get("型号"));
			complain.set("price",price);
			complain.set("buyDate",map.get("购买时间"));
			return complain.addComplain();
		}
	}
	
	/**
	 * 获取所有投诉信息
	 * @return
	 */
	public List<Map<String,Object>> getAllComplainInfo(){
		List<Complain> complainlist = find(Complain.QUERY_ALL_COMPLAININFO);
		List<Map<String,Object>> list = null;
		changeValue(complainlist);
		if(complainlist!=null){
			list = new ArrayList<Map<String,Object>>();
			for(Complain complain:complainlist){
				list.add(complain.getAttrs());
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
