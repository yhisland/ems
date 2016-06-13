package com.manager.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StringKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.manager.config.EmsConfig;

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
//@TableBind(tableName="complain_table",pkName="id")
public class Complain extends Model<Complain> {
	private static final long serialVersionUID = 1L;
	public final static Complain dao = new Complain();
	public final static String QUERY_ALL_COMPLAINCOUNT = "select id from complain_table";
	public final static String QUERY_ALL_COMPLAININFO = "select * from complain_table";
	public final static String QUERY_COMPLAIN_NUMBER = "select * from complain_table where complainPhone=?";

	Page<Record> list;		
	
	/**
	 * 获取指定投诉id的维修记录
	 * 
	 * @param offset
	 * @return List<Maintain>
	 */
	public List<Complain> findPageComplainList(int offset,int userType) {
		System.out.println("Complain进入findPageComplainList**************************************************");
		List<Complain> complainlist = null;
		List<String> sqlList = getPaginateSqlByUserType(userType);
		Page<Complain> pageList = paginate((offset / EmsConfig.pageSize) + 1,
				EmsConfig.pageSize, sqlList.get(0),sqlList.get(1));
		complainlist = pageList.getList();
//		changeValue(complainlist);
		return complainlist;
	}

	/**
	 * 查找所有
	 */		
	public Map<String, Object> selectAll(int page,int pagesize,JSONObject jObject,String sortname,String sortorder){
		System.out.println("Complain进入selectAll**************************************************");
		String select="select *,count(district) as complainSum ";

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
		//排序
		if (StringKit.notBlank(sortname)) {
			sb.append(" order by " +sortname+" "+sortorder );
		}else{
			sb.append("   order by  creatdate  ");
		}*/
		sb.append("   group by  district  ");
		System.out.println("SQL="+select+sb.toString());
		Map<String, Object> map = new HashMap<String, Object>();
		list = Db.paginate(page, pagesize, select, sb.toString());
		map.put("Rows", list.getList());
		map.put("Total", list.getTotalRow());		
		return (map);
	}	
	
	/**
	 * 显示图表
	 */		
	
	public List<Complain> getList(int page,int pagesize,JSONObject jObject,String sortname,String sortorder){
		System.out.println("Complain进入getList**************************************************");
		List<Complain> list = new ArrayList<Complain>();
		System.out.println("jObject.getString()="+jObject.getString("mark"));
		String select="";
		String sqlExceptSelect="";
		StringBuffer sb=new StringBuffer();
		//判断需显示页面类型
		if (StringKit.notBlank(jObject.getString("mark"))) {
			String mark= jObject.getString("mark");
			System.out.println("mark="+mark);
			//按投诉原因分类
			if ("fault".equals(mark)) {
		
				select="select faultCause,count(faultCause) as complainSum "
								 /*+"       creatdate," 
								 +"       round(dsr_qual_score / dsr_qual_comt_cnt, 4) as dsr_qual," 
								 +"       round(dsr_deli_score / dsr_deli_comt_cnt, 4) as dsr_deli," 
								 +"       round(dsr_serv_score / dsr_serv_comt_cnt, 4) as dsr_serv," 
								 +"       round(dsr_logi_score / dsr_logi_comt_cnt, 4) as dsr_logi "*/
						;
				
				sqlExceptSelect=" from complain_table "
								 +" where 1 = 1 ";
		
		//		StringBuffer sb=new StringBuffer();
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
				sb.append("   group by  faultCause  ");
					}
		//			System.out.println("mark="+mark);
					//			sb.append(" and shop_head.shop_head_id = '"+shop_head_id+"'" );
			//按投诉时间分类
			//按72、86分类
/*			else if ("time".equals(mark)) {
				select=" select jobType72, acceptTime72,complainSum72,jobType86, acceptTime86,complainSum86  ";
				sqlExceptSelect=" from (select jobType jobType72, acceptTime acceptTime72,count(acceptTime) as complainSum72 from (select jobType, date_format(acceptTime,'%Y-%m-%d') acceptTime from complain_table where acceptTime >= DATE_ADD(curdate(),interval -day(curdate())+1 day) and acceptTime <= last_day(curdate())and jobType = '7210086' ) acceptTime_table  where 1 = 1    group by  acceptTime) acceptTime_table72 left join(select jobType jobType86, acceptTime acceptTime86,count(acceptTime) as complainSum86 from (select jobType, date_format(acceptTime,'%Y-%m-%d') acceptTime from complain_table where acceptTime >= DATE_ADD(curdate(),interval -day(curdate())+1 day) and acceptTime <= last_day(curdate())and jobType = '10086' ) acceptTime_table  where 1 = 1    group by  acceptTime) acceptTime_table86 on acceptTime_table86.acceptTime86 = acceptTime_table72.acceptTime72 "
						 +" where 1 = 1 ";
				sb.append(sqlExceptSelect);
				sb.append(" ");
			}*/
			//输出当月所有故障
			else if ("time".equals(mark)) {
				select=" select acceptTime,count(acceptTime) as complainSum ";
				sqlExceptSelect=" from (select date_format(acceptTime,'%Y-%m-%d') acceptTime from complain_table "
						 +" where acceptTime >= DATE_ADD(curdate(),interval -day(curdate())+1 day) "
						 +" and acceptTime <= last_day(curdate())) acceptTime_table "
						 +" where 1 = 1 ";
				sb.append(sqlExceptSelect);
				sb.append("   group by  acceptTime  ");
			}
			//按投诉区域分类
			else {
				select="select district,count(district) as complainSum ";
				sqlExceptSelect=" from complain_table "
						 +" where 1 = 1 ";
				sb.append(sqlExceptSelect);
				sb.append(" group by  district order by complainSum Desc ");
			}
		}		
		System.out.println("SQL="+select+sb.toString());
		list = Complain.dao.find(select+sb.toString());
		System.out.println("list="+list);
		return  list;		
	}	
	
	
	
	/**
	 * 查看投诉信息执行
	 * 
	 */	
	private List<String> getPaginateSqlByUserType(int userType){
		System.out.println("Complain进入getPaginateSqlByUserType**************************************************");
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
		System.out.println("Complain进入findLikeComplainList**************************************************");
		List<Complain> complainlist = null;
		complainlist = find("select * from complain_table where site LIKE '%"
				+ condition + "%'");
		System.out.println("findLikeComplainListSLQ = select * from complain_table where site LIKE '%"+ condition + "%'");
//		changeValue(complainlist);
		return complainlist;
	}

	/**
	 * 获取单个投诉转换信息
	 * @param id
	 * @return
	 */
	public Complain findSignleComplain(int id) {
		System.out.println("Complain进入findSignleComplain**************************************************");
		List<Complain> list = new ArrayList<Complain>();
		list.add(findById(id));
//		changeValue(list);
		return list.get(0);
	}
	
	/**
	 * 根据宽带投诉号码获取投诉
	 * @param complainPhone
	 * @return Complain
	 */
	public Complain findComplainByNumber(String complainPhone){
		System.out.println("Complain进入findComplainByNumber**************************************************");
		return findFirst(Complain.QUERY_COMPLAIN_NUMBER,complainPhone);
	}

	/**
	 * 改变投诉类型标志位(暂不使用)
	 * @param complainlist
	 */
	private void changeValue(List<Complain> complainlist) {
		if (null == complainlist || complainlist.size() == 0) {
			return;
		}
//		int currentUserId;
//		int currentStatus;
		int jobType;
		for (Complain complain : complainlist) {
//			currentUserId = Integer.parseInt(complain.get("currentUserId")
//					.toString());
//			currentStatus = Integer.parseInt(complain.get("currentStatus")
//					.toString());
			jobType = Integer.parseInt(complain.get("jobType")
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
			// 工单类型0:10086,2:7210086
			if (jobType == 0) {
				complain.set("jobType", "10086");
			}else {
				complain.set("jobType", "7210086");
			}
/*			// 当前工单状态0:运行，1:完成，2删除
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
			}*/
		}
	}

	/**
	 * 获取表记录总数
	 * 
	 * @return
	 */
	public int getTableCount() {
		System.out.println("Complain进入getTableCount**************************************************");
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
		System.out.println("Complain进入getTableYuYueCount**************************************************");
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
		System.out.println("Complain进入addComplain**************************************************");
//		this.set("currentStatus", 0);
//		this.set("urgencyStatus", 0);
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
//		this.set("currentStatus", 1);
//		this.set("urgencyStatus", 0);
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
		System.out.println("Complain进入addComplainByMany**************************************************");
		if (map == null) {
			return false;
		} else {
//			String userType = map.get("用户品牌").toString();
//			System.out.println("用户品牌="+userType);
/*			String equipNumber = map.get("编号").toString();
			String price = map.get("单价").toString();
			if(findComplainByNumber(equipNumber)!=null||!RegexUtil.regexPrice(price)){
				return false;
			}*/
			Complain complain = new Complain();
//			complain.set("equipNumber",equipNumber);
//			complain.set("userType",userType);
//			complain.set("site",map.get("宽带装机地址"));
/*			complain.set("userType",map.get("用户品牌"));
			complain.set("userCode",map.get("用户资费名称及代码"));
			complain.set("installCity",map.get("安装区县"));
			complain.set("userAcceptTime",map.get("用户预约上门时间"));
//			String serialNum = String.valueOf(map.get("流水号"));
			complain.set("serialNum",map.get("流水号"));
//			double serialNum=new Double(map.get("流水号").toString()); 
//			System.out.println("流水号="+serialNum);
			complain.set("disposeStatusOne",map.get("解决程度"));
			complain.set("acceptTime",map.get("受理时间"));
			complain.set("currentStatus",map.get("工单状态"));
			complain.set("complainCity",map.get("投诉地市"));
			complain.set("acceptCity",map.get("受理地市"));
			complain.set("complainPhone",map.get("受理号码"));
			complain.set("contactPhone",map.get("联系电话"));
			complain.set("userName",map.get("用户姓名"));
			complain.set("userPart",map.get("用户归属局"));
			complain.set("serviceSort",map.get("服务请求类别"));
			complain.set("userGrade",map.get("用户级别"));
			complain.set("urgencyStatus",map.get("紧急程度"));
			complain.set("jobNum",map.get("受理工号"));
			complain.set("content",map.get("业务内容"));
			complain.set("title",map.get("工单标题"));
			complain.set("acceptManner",map.get("受理方式"));
			complain.set("acceptWay",map.get("受理渠道"));
			complain.set("cause",map.get("责任定性"));
			complain.set("disposeStatus",map.get("解决程度"));
			complain.set("SPService",map.get("SP服务代码"));
			complain.set("site",map.get("宽带装机地址"));
			complain.set("faultSite",map.get("客户故障地址"));
			complain.set("street",map.get("街道地址"));
			complain.set("district",map.get("区域"));
			complain.set("community", map.get("小区"));
			complain.set("responsible",map.get("区域负责人"));
			complain.set("worker",map.get("维护员"));
			complain.set("faultCause",map.get("故障原因"));
			complain.set("faultDetail",map.get("故障详情"));*/
			
/*			complain.set("equipName",map.get("名称"));
			complain.set("equipModel",map.get("型号"));
			complain.set("price",price);
			complain.set("buyDate",map.get("购买时间"));*/
			
			System.out.println("map="+map);
			String str =(String)map.get("工单创建时间");
			System.out.println("工单创建时间str="+str);	
			System.out.println("用户姓名="+map.get("用户姓名"));	

			complain.set("jobType",map.get("工单类型"));
			complain.set("acceptTime",map.get("工单创建时间"));
			complain.set("timeOut",map.get("超4小时时间"));
			complain.set("userName",map.get("用户姓名"));
			complain.set("complainPhone",map.get("账号"));
			complain.set("contactPhone",map.get("联系电话"));
			complain.set("describe",map.get("故障描述"));
			complain.set("faultSite",map.get("用户地址"));
			complain.set("district",map.get("网格"));
			complain.set("community",map.get("小区"));
			complain.set("worker",map.get("维护人员"));
			complain.set("faultType",map.get("故障类型"));
			complain.set("faultCause",map.get("故障真实原因"));
			complain.set("disposeStatus",map.get("处理结果"));
			complain.set("untreatedCause",map.get("未处理原因"));
			if(map.get("反馈时间")==""){
				complain.set("feedbackTime","1900-01-01 00:00:00");
			}else{
				complain.set("feedbackTime",map.get("反馈时间"));
			}
			complain.set("finishTime",map.get("完成时间"));
			complain.set("handleTime",map.get("故障处理时长"));
			complain.set("overTimeCause",map.get("超时原因（10小时）"));
			if(map.get("工单打回时间")==""){
				complain.set("rejectTime","1900-01-01 00:00:00");
			}else{
				complain.set("rejectTime",map.get("工单打回时间"));
			}
			if(map.get("打回结单时间")==""){
				complain.set("rejectFinishTime","1900-01-01 00:00:00");
			}else{
				complain.set("rejectFinishTime",map.get("打回结单时间"));
			}			
			complain.set("facility",map.get("接入设备型号"));	
			complain.set("port",map.get("接入端口号"));
			complain.set("complainCause",map.get("用户投诉原因"));
			complain.set("remark",map.get("用户投诉原因(备注)"));

			return complain.addComplain();
		}
	}
	
	/**
	 * 获取所有投诉信息
	 * @return
	 */
	public List<Map<String,Object>> getAllComplainInfo(){
		System.out.println("Complain进入getAllComplainInfo**************************************************");
		List<Complain> complainlist = find(Complain.QUERY_ALL_COMPLAININFO);
		List<Map<String,Object>> list = null;
//		changeValue(complainlist);
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
