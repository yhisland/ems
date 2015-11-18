package com.manager.config;

import org.apache.log4j.Logger;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.manager.controller.StaticResourceController;
import com.manager.controller.UserController;
import com.manager.model.User;

/**   
 * 类名称：JinalConfig   
 * 类描述：jfinal配置
 * 创建人：chenjun    yhisland@163.com
 * 创建时间：2015年1月28日 下午7:41:12   
 * 修改人：chenjun    yhisland@163.com    
 * 修改时间：2015-8-9上午12:10:29   
 * 修改备注：数据库插件        
 *    
 */ 


	/**
	 * API引导式配置
	 */
public class JfinalConfig extends JFinalConfig {
	
	private static Logger log = Logger.getLogger(JfinalConfig.class);
	/**每页显示的记录条数*/
	public final static int pageSize = 15;
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
//		loadPropertyFile("a_little_config.txt");
		
    	// 加载少量必要配置，随后可用getProperty(...)获取值
        loadPropertyFile("a_little_config.txt");                
        me.setDevMode(false);
        // 设置视图类型为Jsp，否则默认为FreeMarker
        me.setViewType(ViewType.JSP);   
/*		log.info("configConstant 缓存 properties");
		new PropertiesPlugin(loadPropertyFile("init.properties")).start();		
		
		//  加载 beetl 
		log.info("configConstant 视图Beetl设置");
		me.setMainRenderFactory(new BeetlRenderFactory());
		me.setDevMode(true);  */
	//	me.setDevMode(getPropertyToBoolean("devMode",true));//效果同上
	
	}
	
	/**
	 * 配置路由   自动绑定路由
	 */
	public void configRoute(Routes me) {
/*		log.info("configRoute 路由扫描注册");
		me.add(new AutoBindRoutes());*/
//      me.add("/hello", com.manager.controller.HelloWorldController.class);  	
		
        //静态资源跳转控制器
        me.add("/",StaticResourceController.class);
        me.add("user",UserController.class,"/");
		
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
/*		log.info("configPlugin 配置Druid数据库连接池连接属性");
		DruidPlugin druidPlugin = new DruidPlugin(
				(String)PropertiesPlugin.getParamMapValue("jdbc:mysql://127.0.0.1:3306/jdbc"), 
				(String)PropertiesPlugin.getParamMapValue("root"), 
				(String)PropertiesPlugin.getParamMapValue("cjadmin999"), 
				(String)PropertiesPlugin.getParamMapValue("com.mysql.jdbc.Driver"));

		
		log.info("configPlugin 配置ActiveRecord插件");
		ActiveRecordPlugin arpMain = new ActiveRecordPlugin(DictKeys.db_dataSource_main, druidPlugin);
		//arp.setTransactionLevel(4);//事务隔离级别
		arpMain.setDevMode(getPropertyToBoolean(DictKeys.config_devMode, false)); // 设置开发模式
		arpMain.setShowSql(getPropertyToBoolean(DictKeys.config_devMode, false)); // 是否显示SQL

		log.info("configPlugin 使用数据库类型是 mysql");
		arpMain.setDialect(new MysqlDialect());
		arpMain.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 小写
*/

		
	     // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        me.add(c3p0Plugin);
        
     // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        arp.addMapping("user_table",User.class);
		
/*        C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://127.0.0.1:3306/ems",
                "root", "cjadmin999");
        cp.setDriverClass("com.mysql.jdbc.Driver");
        me.add(cp);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        me.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 小写
*///		arp.addMapping("user", User.class);
//		arp.addMapping("article", "article_id", Article.class);		
		
		
/*		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(
				getProperty("jdbcUrl"),
				getProperty("user"),
				getProperty("password").trim(),"oracle.jdbc.driver.OracleDriver");
		me.add(c3p0Plugin);
		
		// 添加自动绑定model与表插件
		AutoTableBindPlugin atbp = new AutoTableBindPlugin(c3p0Plugin,SimpleNameStyles.LOWER);   
		atbp.setShowSql(true);		
		atbp.setDialect(new OracleDialect());
		atbp.setContainerFactory(new CaseInsensitiveContainerFactory());// 配置属性名(字段名)大小写不敏感容器工厂
		me.add(atbp);*/
		
/*		String decPassWord =getProperty("password");
		String decUser =getProperty("user");
		DruidPlugin druid = new DruidPlugin(getProperty("url"),decUser,decPassWord);
		me.add(druid);//添加连接池
		ActiveRecordPlugin arp =new ActiveRecordPlugin(druid);
		me.add(arp);//接入ActiveRecordPlugin插件
		arp.addMapping("t_user",User.class);//实体与表的映射		
*/	
		
/*		log.info("configPlugin 配置Druid数据库连接池连接属性");
		DruidPlugin druidPlugin = new DruidPlugin(
				(String)PropertiesPlugin.getParamMapValue(DictKeys.db_connection_jdbcUrl), 
				(String)PropertiesPlugin.getParamMapValue(DictKeys.db_connection_userName), 
				(String)PropertiesPlugin.getParamMapValue(DictKeys.db_connection_passWord), 
				(String)PropertiesPlugin.getParamMapValue(DictKeys.db_connection_driverClass));
		log.info("configPlugin 配置Druid数据库连接池大小");
		druidPlugin.set(
				(Integer)PropertiesPlugin.getParamMapValue(DictKeys.db_initialSize), 
				(Integer)PropertiesPlugin.getParamMapValue(DictKeys.db_minIdle), 
				(Integer)PropertiesPlugin.getParamMapValue(DictKeys.db_maxActive));
		
		log.info("configPlugin 配置ActiveRecord插件");
		ActiveRecordPlugin arpMain = new ActiveRecordPlugin(DictKeys.db_dataSource_main, druidPlugin);
		//arp.setTransactionLevel(4);//事务隔离级别
		arpMain.setDevMode(getPropertyToBoolean(DictKeys.config_devMode, false)); // 设置开发模式
		arpMain.setShowSql(getPropertyToBoolean(DictKeys.config_devMode, false)); // 是否显示SQL

		log.info("configPlugin 数据库类型判断");
		String db_type = (String) PropertiesPlugin.getParamMapValue(DictKeys.db_type_key);
		if(db_type.equals(DictKeys.db_type_postgresql)){
			log.info("configPlugin 使用数据库类型是 postgresql");
			arpMain.setDialect(new PostgreSqlDialect());
			
		}else if(db_type.equals(DictKeys.db_type_mysql)){
			log.info("configPlugin 使用数据库类型是 mysql");
			arpMain.setDialect(new MysqlDialect());
			arpMain.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 小写
		
		}else if(db_type.equals(DictKeys.db_type_oracle)){
			log.info("configPlugin 使用数据库类型是 oracle");
			druidPlugin.setValidationQuery("select 1 FROM DUAL"); //指定连接验证语句(用于保存数据库连接池), 这里不加会报错误:invalid oracle validationQuery. select 1, may should be : select 1 FROM DUAL 
			arpMain.setDialect(new OracleDialect());
			arpMain.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 配置属性名(字段名)大小写不敏感容器工厂
		}*/
	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		//登录全局拦截器
		//me.add(new LoginInterceptor());  
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		//me.add(new SessionHandler());
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
