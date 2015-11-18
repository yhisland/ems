/**
 * 
 */
package com.manager.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.manager.limit.LimitSystem;
import com.manager.model.User;


/**
 *验证用户左侧菜单操作权限：AOP面向切面编程
 *无法拦截对静态资源访问的请求，原因：参考底层ActionHandler中handler()方法的实现.
 *只能实现对具体Action操作的拦截
 * 
 */
public class EmsLimitInterceptor implements Interceptor {
	
	@Override
	public void intercept(ActionInvocation ai) {
		User user = ai.getController().getSessionAttr("user");
		String url =ai.getActionKey();
		if(user!=null){
			if(LimitSystem.limitSystem.ishaveLimit(user.getInt("userType"), url)){
				ai.invoke();
			}else{
				ai.getController().redirect("/nolimit.html");
			}
		}else{
		   ai.invoke();
		}
	}

}
