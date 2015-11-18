<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主页面—center</title>
<style type="text/css">
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	overflow: hidden;
}

div.taskTip {
	display: none;
	width: 220px;
	height: 150px;
	border: 1px solid #6C9BA1;
	position: fixed;
	right: 9px;
	bottom:0px;
	z-index: 9999;
}
}
</style>
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<c:if test="${sessionScope.user.get('userType')==0}">
	<script type="text/javascript">
		$(function() {
			setTimeout(sendRequest, 1000);
		});

		function sendRequest() {
			$.post("/user/getTask", null, callBack, "json");
		}

		function callBack(result) {
			if (!isNaN(result) && result > 0) {
				$("span.tipInfo").text("您有" + result + "条待审核的任务!");
				$("div.taskTip").slideDown(1000);
			}
		}

		function hideTip() {
			$("div.taskTip").slideUp(1000);
		}

		function goDeviceList() {
			window.open("/device/queryPageDevice?offset=1", "main_frame");
			hideTip();
		}
	</script>
</c:if>
</head>
<body>
	<table width="100%" height="100%" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td width="8" bgcolor="#353c44">&nbsp;</td>
			<td width="147" valign="top"><iframe height="100%" width="100%"
					border="0" frameborder="0" src="/left.html" name="leftFrame"
					id="leftFrame" title="leftFrame"></iframe></td>
			<td width="10" bgcolor="#add2da">&nbsp;</td>
			<td valign="top"><iframe height="100%" width="100%" border="0"
					frameborder="0" src="/welcome.html" name="main_frame"
					id="main_frame" title="main_frame"></iframe></td>
			<td width="8" bgcolor="#353c44">&nbsp;</td>
		</tr>
	</table>
	<div class="taskTip">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="30"
					style="background: url(/images/crm_18.gif) repeat-x;">
					<img src="/images/close.gif" style="cursor: pointer; float: right; margin-right: 5px;"
					onclick="javascript:hideTip()">
					</td>
			</tr>
			<tr onclick="javascript:goDeviceList()" style="cursor: pointer;"
				title="查看任务">
				<td height="120" align="center" valign="middle"
					style="background: url(/images/crm_24.gif) repeat;"><span
					class="tipInfo" style="font-size:12px;"></span></td>
			</tr>
		</table>
	</div>
</body>
</html>
