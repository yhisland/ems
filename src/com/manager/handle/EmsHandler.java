/**
 * 
 */
package com.manager.handle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * 自定义请求处理器：接收并处理所有的请求。
 * JFinal默认的请求处理器：ActionHandler。该处理器仅能处理所有的action请求（拦截器，验证器都是在此基础上执行的）。而对于
 * 静态资源(例如：.jsp文件等是不做任何逻辑处理的，所以对于拦截器要想拦截静态资源的请求，是行不通的。详情：请参考ActionHandler底层
 * handle()方法的实现原理)。可以自定义Handler来代替ActionHandler实现拦截静态资源的逻辑。
 * if (target.indexOf(".") != -1) {//ActionHandler中handle()方法对静态文件访问的请求(例如:/addUser.jsp)进行了拦截处理。
 *		return ;//所以对于静态资源访问的请求根本不会进入到自定义的拦截器,验证器，Controller等（全局或局部）中的处理逻辑去。
 *	}
 * 
 */
public class EmsHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
	}

}
