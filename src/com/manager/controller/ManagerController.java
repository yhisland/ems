package com.manager.controller;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.ext.route.ControllerBind;
//import com.manager.model.Login;

/**   
 * 类名称：ManagerContoller  
 * 类描述：manager首页
 * 创建人：Administrator
 * 创建时间： 2015-8-5下午3:46:46
 * 修改人：Administrator   
 * 修改时间：2015-8-5下午3:46:46 
 * 修改备注：        
 *    
 */ 

@ControllerBind(controllerKey = "manager")
public class ManagerController extends Controller {
		public void index (){
	        render("/index.htm");
        }
/*		public void searchAll(){
			List<Login> list	 = Login.dao.gettotal();
//			renderJson(JsonKit.listToJson(list, 3));
		}*/
}
