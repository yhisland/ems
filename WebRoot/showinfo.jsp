<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改用户信息——当前用户</title>
<style>
input[type="text"] {
	display: inline-block;
	width: 260px;
	height: 30px;
	line-height: 28px;
	border: 1px solid #cadcb2;
	text-align: center;
}

span {
	color: red;
	font-size: 12px;
}
</style>
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/js/ems.js"></script>
<script type="text/javascript">
	function validateTelNumber(input) {
		var telNumber = $.trim($(input).val());
		if (Ems.validateTelNumber(telNumber)) {
			$("span.validate").text("");
		} else {
			$("span.validate").text("号码不合法!");
		}
	}

	function validate() {
		if ($("span.validate").text() != "") {
           alert("请输入合法的联系电话!");
           return false;
		}
		return true;
	}
</script>
</head>

<body>
	<form action="/user/updateUserInfo" method="post"
		onsubmit="return validate();">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="font-size: 15px;">
			<tr>
				<td height="79" align="center" valign="middle"><h1>展示用户信息</h1></td>
			</tr>
			<tr>
				<td height="35" align="center" valign="middle"><c:if
						test="${requestScope.info!=null}">
						<span>${requestScope.info}</span>
					</c:if></td>
			</tr>
			<tr>
				<td height="280" align="center" valign="middle"><table
						width="50%" border="0" cellspacing="5" cellpadding="0">
						<tr>
							<td width="27%" height="49" align="right" valign="middle">用户名：</td>
							<td width="73%"><input type="text" class="username"
								name="username" value="${sessionScope.user.get('username')}"
								disabled="disabled" /></td>
						</tr>
						<tr>
							<td height="50" align="right" valign="middle">真实姓名：</td>
							<td><input type="text" class="name" name="name"
								value="${sessionScope.user.get('name')}" /></td>
						</tr>
						<tr>
							<td height="50" align="right" valign="middle">用户类型：</td>
							<td><input type="text" class="userType" name="userType"
								value="${sessionScope.user.get('userTypeText')}"
								disabled="disabled" /></td>
						</tr>
						<tr>
							<td height="50" align="right" valign="middle">联系电话：</td>
							<td><input type="text" class="telNumber" name="telNumber"
								value="${sessionScope.user.get('telNumber')}"
								onblur="javascript:validateTelNumber(this);" />&nbsp;&nbsp;<span
								class="validate"></span></td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td height="52" align="center" valign="middle"><input
					type="submit" value="修  改"
					style="width: 80px; height: 28px; cursor: pointer; border: 1px solid #cadcb2" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
