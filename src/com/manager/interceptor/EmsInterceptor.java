package com.manager.interceptor;

import javax.servlet.http.HttpSession;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;

/**
 *拦截器：验证用户是否已登录：AOP面向切面编程
 *无法拦截对静态资源访问的请求，原因：参考底层ActionHandler中handler()方法的实现.
 *只能实现对具体Action操作的拦截
 * 
 */
public class EmsInterceptor implements Interceptor{

	@Override
	public void intercept(ActionInvocation ai) {
	HttpSession session = ai.getController().getSession();
	String actionKey = ai.getActionKey();
	//如果当前操作是进入登录界面或登录操作时，则直接放行
	if("/".equals(actionKey)||"/user/login".equals(actionKey)){
		ai.invoke();
	}else{
		if(session!=null&&session.getAttribute("user")!=null){
			ai.invoke();
		}else{
			ai.getController().redirect("/login.jsp");
		}
	}
  }
}
