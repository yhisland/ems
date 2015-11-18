/**
* 文件名：EmsConfig.java
*/
package com.manager.config;

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
import com.manager.controller.ComplainController;
import com.manager.controller.DeviceController;
import com.manager.controller.LogController;
import com.manager.controller.MaintainController;
import com.manager.controller.StaticResourceController;
import com.manager.controller.UserController;
import com.manager.interceptor.EmsInterceptor;
import com.manager.interceptor.EmsLimitInterceptor;
import com.manager.model.Device;
import com.manager.model.Complain;
import com.manager.model.Log;
import com.manager.model.Maintain;
import com.manager.model.User;

/**
 * 功能描述：
 *
 */
public class EmsConfig extends JFinalConfig
{
	/**每页显示的记录条数*/
	public final static int pageSize = 15;
    @Override
    public void configConstant(Constants me)
    {
    	// 加载少量必要配置，随后可用getProperty(...)获取值
        loadPropertyFile("a_little_config.txt");                
        me.setDevMode(true);//////
        // 设置视图类型为Jsp，否则默认为FreeMarker
        me.setViewType(ViewType.JSP);                          

    }

    @Override
    public void configRoute(Routes me)
    {
        //静态资源跳转控制器
        me.add("/",StaticResourceController.class);
        //用户操作控制器
        me.add("user",UserController.class,"/");
        //设备操作控制器
        me.add("device",DeviceController.class,"/");
        //投诉操作控制器
        me.add("complain",ComplainController.class,"/");        
        //设备维修记录操作控制器
        me.add("maintain",MaintainController.class,"/");
        //系统日志操作控制器
        me.add("log",LogController.class,"/");
    }

    /**
     *添加插件链:底层维护着一个列表List
     */
    @Override
    public void configPlugin(Plugins me)
    {
     // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
        me.add(c3p0Plugin);
        
     // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        me.add(arp);
        arp.addMapping("user_table",User.class);
        arp.addMapping("equip_table",Device.class);
        arp.addMapping("complain_table",Complain.class); /////////
        arp.addMapping("maintain_table",Maintain.class);
        arp.addMapping("log_table",Log.class);
    }
   
    /**
     *添加拦截器链:底层维护着一个列表List
     *Jfinal中默认的ActionHandler无法实现拦截器对静态资源的访问请求进行拦截。
     *只能实现对Action具体动作的拦截和验证
     */
    @Override
    public void configInterceptor(Interceptors me)
    {
               //添加全局拦截器：判断用户是否已登录
          	me.add(new EmsInterceptor());
         	//添加全局拦截器：验证用户左侧菜单操作权限
        	me.add(new EmsLimitInterceptor());
    }
    
     /**
      * 添加处理器链
      * 链头部要么是自定义的Handler,否则就只能一个默认的ActionHandler(不存在链)
      * 因为只要添加自定义的Handler，ActionHandler就会失效。
      */
    @Override
    public void configHandler(Handlers me)
    {
      // me.add(new EmsHandler());
    }
    
    public static void main(String[] args)
    {
        JFinal.start("WebRoot", 80, "/", 5);
    }
}
