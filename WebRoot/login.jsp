<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备管理系统——登录</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/js/js.js"></script>
<script type="text/javascript">
	if (self != top) {//避免在iframe框架中，或frameset中因未登陆，而在局部窗口跳转到登录页
		top.location = self.location;
	}
	//登录验证
	function validate() {
		var usernameValue = $(".username").val();
		var passwordValue = $(".password").val();
		if (usernameValue == "" || passwordValue == "") {
			alert("请输入用户名和密码！");
			return false;
		}
		return true;
	}

	function loginSys() {
		if (validate()) {
			form_submit();
		} else {
			return false;
		}
	}

	/**enter键登录系统*/
	function keyboardsubmit(event) {
		event = (event) ? event : ((window.event) ? window.event : null);//浏览器兼容性问题，window.event IE标准搞法
		if (event.keyCode == 13) {
			form_submit();
		} else {
			return false;
		}
	}
</script>
</head>
<body>
	<div id="top"></div>
	<form id="login" name="login" action="/user/login" method="post">
		<div id="center">
			<div id="center_left"></div>
			<div id="center_middle">
				<div class="user">
					<label>用户名： <input type="text" name="username"
						class="username" />
					</label>
				</div>
				<div class="user">
					<label>密&nbsp; 码： <input type="password" name="password"
						class="password" onkeydown="javascript:keyboardsubmit(event);" />
					</label>
				</div>
				<c:if test="${requestScope.errorMsg!=null}">
					<div class="user" style="color: red;text-align:right;">
						<span class="errorInfo">${requestScope.errorMsg}</span>
					</div>
				</c:if>
			</div>
			<div id="center_middle_right"></div>
			<div id="center_submit">
				<div class="button">
					<img src="/images/dl.gif" width="57" height="20"
						onclick="javascript:loginSys();">
				</div>
				<div class="button">
					<img src="/images/cz.gif" width="57" height="20"
						onclick="form_reset()">
				</div>
			</div>
			<div id="center_right"></div>
		</div>
	</form>
	<div id="footer"></div>
</body>
</html>