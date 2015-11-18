/**
 * 
 */
package com.manager.validator;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * 过滤器其实和拦截器是一样的，因为 Validator抽象类底层实现了Interceptor接口
 * 并默认提供了对邮箱验证的方法validateEmail等。
 * 
 */
public class EmsValidator extends Validator {

	@Override
	protected void validate(Controller c) {

	}

	@Override
	protected void handleError(Controller c) {

	}

}
