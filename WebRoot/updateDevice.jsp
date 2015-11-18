<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改设备信息</title>
<style>
input[type="text"] {
	display: inline-block;
	width: 260px;
	height: 30px;
	line-height: 28px;
	text-align: center;
	border: 1px solid #cadcb2;
}

span {
	color: red;
	font-size: 12px;
}
</style>
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/js/ems.js"></script>
<script type="text/javascript" charset="utf-8">
	function validateNumber(input) {
		var number = $.trim($(input).val());
		if (Ems.validateNumber(number)) {
			$("span.validate").text("");
		} else {
			$("span.validate").text("价格不合法!");
		}
	}

	function validate() {
		if ($("span.validate").text() != "") {
			alert("请输入合法的价格!");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<form action="/device/updateDevice" method="post"
		onsubmit="return validate();">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="80" align="center" valign="middle"><h1>修改设备信息</h1></td>
			</tr>
			<tr>
				<td height="300" align="center" valign="middle"><table
						width="50%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="27%" height="46" align="right" valign="middle">设备编号：</td>
							<td width="73%"><input type="text" class="equipNumber"
								name="device.equipNumber" value="${device.equipNumber}" /></td>
						</tr>
						<tr>
							<td height="47" align="right" valign="middle">设备名称：</td>
							<td><input type="text" class="equipName"
								name="device.equipName" value="${device.equipName}" /></td>
						</tr>
						<tr>
							<td height="45" align="right" valign="middle">设备型号：</td>
							<td><input type="text" class="equipModel"
								name="device.equipModel" value="${device.equipModel}" /></td>
						</tr>
						<tr>
							<td height="48" align="right" valign="middle">设备价格：</td>
							<td><input type="text" class="price" name="device.price"
								value="${device.price}" onblur="validateNumber(this)" />&nbsp;&nbsp;<span
								class="validate"></span></td>
						</tr>
						<tr>
							<td height="45" align="right" valign="middle">使用情况：</td>
							<td><input type="radio" class="currentSituation1"
								name="device.currentSituation" value="0" />正常
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio"
								class="currentSituation2" name="device.currentSituation"
								value="1" />损坏</td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td height="50" align="center" valign="middle"><input
					type="submit" value="修  改"
					style="width: 80px; height: 28px; cursor: pointer; border: 1px solid #cadcb2" /></td>
			</tr>
		</table>
		<div class="id_div">
			<span>设备id：</span><input type="text" class="id" name="id"
				value="${device.id}" />
		</div>
	</form>
	<script type="text/javascript">
		$(function() {
			var selectValue = "${device.currentSituation}";
			if (selectValue == 0) {
				$(".currentSituation1").attr("checked", "checked");
			} else {
				$(".currentSituation2").attr("checked", "checked");
			}
			$("div.id_div").css("display", "none");
		});
	</script>
</body>
</html>
