package com.manager.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
//import com.jfinal.ext.route.AutoBindRoutes;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;

/**   
 * 类名称：DemoConfig   
 * 类描述：jfinal配置
 * 创建人：chenjun    yhisland@163.com
 * 创建时间：2015年1月28日 下午7:41:12   
 * 修改人：chenjun    yhisland@163.com    
 * 修改时间：2015-8-9上午12:10:29   
 * 修改备注：数据库插件        
 *    
 */ 
public class DemoConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
    }

    @Override
    public void configHandler(Handlers me) {
        // TODO Auto-generated method stub

    }

    @Override
    public void configInterceptor(Interceptors me) {
        // TODO Auto-generated method stub

    }

    @Override
    public void configPlugin(Plugins me) {
        C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://127.0.0.1:3306/jdbc",
                "root", "cjadmin999");
        cp.setDriverClass("com.mysql.jdbc.Driver");
        me.add(cp);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        me.add(arp);
		arp.setDialect(new MysqlDialect());
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));// 小写
    }

    @Override
    public void configRoute(Routes me) {
//		me.add(new AutoBindRoutes());
//        me.add("/", com.manager.controller.StudentController.class);
//        me.add("/student", com.manager.controller.StudentController.class);
//        me.add("/classes", ClassesController.class);
    }
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
