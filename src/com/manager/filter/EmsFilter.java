/**
 * 
 */
package com.manager.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.manager.limit.LimitSystem;
import com.manager.model.User;


/**
 * 登录，权限验证过滤器
 * 结论：对静态资源(.png，.css,.js,.jpg，jsp,html等)拦截方式，做登录，权限验证是行不通的
 * 因为在一个login页面中很可能会存在其他静态的资源（例如：图片资源）。拦截之后，图片都加载不下来。
 * 所以，只能针对Action动作做登录验证和权限处理。
 */
public class EmsFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("EmsFilter destroy");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		HttpSession session = request.getSession();
		String targetI = request.getRequestURI();
		String targetR = request.getRequestURL().toString();
		String contextPath = request.getContextPath();
//		String realPath = request.getRealPath("");
		System.out.println("RequestURI:"+targetI);//images/login_08.gif
		System.out.println("RequestURL:"+targetR);//http://localhost/images/login_08.gif
		System.out.println("ContextPath:"+contextPath);//""
//		System.out.println("realPath:"+realPath);//项目真实访问根目录D:\workplace\ems\ems\WebRoot
		User user = (User) session.getAttribute("user");
		if(session!=null&&user!=null){
			if(LimitSystem.limitSystem.ishaveLimit((Integer)(user.get("userType")), targetI)){
				chain.doFilter(request, response);
			}else{
				request.getRequestDispatcher("/nolimit.html").forward(request, response);//请求转发到nolimit.html页面
			}
		}else{
//			response.sendRedirect("/login.jsp");//重定向到登录页面,此处不能重定向，会死循环的。要有请求转发，
		    //因为请求转发是不会再经过servlet容器的。
			request.getRequestDispatcher("/login.jsp").forward(request, response);//当加载一个页面，而其中存在多个静态资源时(.js文件，
			//图片,.css文件等)会发送多次http请求(http1.1长连接，共用同一个连接而已)。也就是说还是会循环。但是，不是死循环，毕竟一个页面
			//中的静态资源数是有限的。
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
       System.out.println("EmsFilter init");
	}

}
