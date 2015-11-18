<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改用户信息——管理员</title>
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
	<form method="post" action="/user/updateUserByAdmin" onsubmit="return validate();">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="80" align="center" valign="middle"><h1>修改用户信息</h1></td>
			</tr>
			<tr>
				<td height="309" align="center" valign="middle"><table
						width="50%" border="0" cellspacing="5" cellpadding="0">
						<tr>
							<td width="27%" height="44" align="right">用户名：</td>
							<td width="73%"><input type="text" class="username"
								name="user.username"
								value="${requestScope.user.get('username')}" /></td>
						</tr>
						<tr>
							<td height="46" align="right">真实姓名：</td>
							<td><input type="text" class="name" name="user.name"
								value="${requestScope.user.get('name')}" /></td>
						</tr>
						<tr>
							<td height="47" align="right">用户类型：</td>
							<td><input type="radio" class="userType1"
								name="user.userType" value="0" />管理员&nbsp;&nbsp;&nbsp; <input
								type="radio" class="userType2" name="user.userType" value="1" />教师&nbsp;&nbsp;&nbsp;
								<input type="radio" class="userType3" name="user.userType"
								value="2" />学生</td>
						</tr>
						<tr>
							<td height="48" align="right">联系电话：</td>
							<td><input type="text" class="telNumber"
								name="user.telNumber"
								value="${requestScope.user.get('telNumber')}"
								onblur="javascript:validateTelNumber(this);"/>&nbsp;&nbsp;<span
								class="validate"></span></td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td height="50" align="center" valign="middle"><input
					type="submit" value="修  改"
					style="width: 80px; height: 28px; cursor: pointer; border: 1px solid #cadcb2" /></td>
			</tr>
		</table>
		<input type="text" class="id" name="id"
			value="${requestScope.user.get('id')}" />
		<script type="text/javascript">
			$(function() {
				var selectValue = "${user.userType}";
				if (selectValue == 0) {
					$(".userType1").attr("checked", "checked");
				} else if (selectValue == 1) {
					$(".userType2").attr("checked", "checked");
				} else {
					$(".userType3").attr("checked", "checked");
				}
				$("input.id").css("display", "none");
			});
		</script>
	</form>
</body>
</html>
