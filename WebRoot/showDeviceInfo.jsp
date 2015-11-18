<%@page import="java.util.List"%>
<%@page import="com.manager.model.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/mypage.css" type="text/css"
	media="screen" title="no title" charset="utf-8" />
<link rel="stylesheet" href="/css/leanModel_style.css" type="text/css"
	media="screen" title="no title" charset="utf-8" />
<link rel="stylesheet" href="/css/table.css" type="text/css"
	media="screen" title="no title" charset="utf-8" />
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/js/jquery-pagination.js"></script>
<script type="text/javascript" charset="utf-8"
	src="/js/jquery.leanModal.min.js"></script>
<script type="text/javascript" src="/js/table_js.js"></script>
<script type="text/javascript">
	function validate() {
		var condition = $("input.condition").val();
		if (condition == null || $.trim(condition) == "") {
			alert("请输入查询条件！");
			return false;
		}
		return true;
	}
	$(function() {
		$('.modaltrigger').leanModal({
				top:110,
				closeButton : ".hidemodal",
		});
	});
</script>
</head>
<body>
	<h1>设备信息列表</h1>
	<table>
		<thead>
			<tr>
				<th><input
					onClick="if(this.checked==true) { checkAll('test'); } else { clearAll('test'); }"
					type="checkbox" value="" name="test" title="全选/取消" /></th>
				<th>设备编号</th>
				<th>设备名称</th>
				<th>设备型号</th>
				<th>设备价格</th>
				<th>购买日期</th>
				<th>使用情况</th>
				<th>当前状态</th>
				<th>使用人员</th>
				<th>归还期限</th>
				<th>设备操作</th>
			</tr>
		</thead>
		<tbody id="tab">
			<c:forEach items="${requestScope.deviceList}" var="device"
				varStatus="status">
				<tr>
					<td><input type="checkbox" value="${device.id}" name="test" /></td>
					<td>${device.equipNumber}</td>
					<td>${device.equipName}</td>
					<td>${device.equipModel}</td>
					<td>${device.price}</td>
					<td>${device.buyDate}</td>
					<td>${device.currentSituation}</td>
					<td class="state${status.index}">${device.currentState}</td>
					<c:if test="${device.currentState=='预约'}">
						<script type="text/javascript">
							$("td.state${status.index}").css("color", "red");
						</script>
					</c:if>
					<td>${device.currentUser}</td>
					<td>${device.returnDate}</td>
					<td><c:if test="${sessionScope.user.get('userType')==0}">
					&nbsp;&nbsp;<a href="/device/deleteDevice/${device.id}"
								onclick="return window.confirm('您确定要删除?');">删除</a>
					&nbsp;&nbsp;<a href="/device/edit/${device.id}">修改</a>
							<c:if test="${device.currentState=='借出'}">
					&nbsp;&nbsp;<a href="/device/back/${device.id}">归还</a>
							</c:if>
						</c:if> <c:if
							test="${device.currentState=='在库'&&sessionScope.user.get('userType')!=0&&device.currentSituation=='正常'}">
					&nbsp;&nbsp;<a href="#subscribeDateBox" class="modaltrigger" name="${device.id}">预约</a>
						</c:if> <c:if
							test="${device.currentState=='预约'&& sessionScope.user.get('userType')==0}">
					&nbsp;&nbsp;<a href="/device/audit/${device.id}">审核</a>
						</c:if> <c:if test="${sessionScope.user.get('userType')!=2}">
				    &nbsp;&nbsp;<a href="/maintain/queryPageMaintain/${device.id}-1">维修记录</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td><c:choose>
						<c:when test="${sessionScope.user.get('userType')==0}">
							<img src="/images/delete.ico" title="删除选中项"
								style="cursor: pointer;"
								onclick="javascript:deleteSelected('/device/deleteAllSelected','/device/queryPageDevice?offset=1')" />
						</c:when>
						<c:otherwise>
							<input
								onClick="if(this.checked==true) { checkAll('test'); } else { clearAll('test'); }"
								type="checkbox" value="" name="test" title="全选/取消" />
						</c:otherwise>
					</c:choose></td>
				<td>
					<form method="post" action="/device/queryDeviceByLike"
						onsubmit="return validate()">
						<input type="text" name="condition" class="condition"
							style="width: 150px; line-height: 18px; height: 20px; border: 1px solid #cadcb2"
							title="请输入设备名称" /> <input type="submit"
							style="width: 45px; height: 24px; cursor: pointer; border: 1px solid #cadcb2"
							value="查 询" />
					</form>
				</td>
				<td colspan="9">
					<div class="mypage">
						<script type="text/javascript">
							$(".mypage")
									.pager(
											{
												nTotal : "${requestScope.num}",
												nTheme : 1,
												nPageSize : 15,
												nWidth : 24,
												sUrl : "/user/queryPageUser",
												sPrevFlag : "<上一页",
                                                sNextFlag:"下一页>",
											});
						</script>
					</div>
				</td>
			</tr>
		</tfoot>
	</table>
	<div id="subscribeDateBox">
		<form action="/device/subscribe" method="post"
			style="text-align: center; font-size: 13px;">
			<div class="div1">
				预约备注：
				<textarea name="subscribeNote"
					style="width: 200px; height: 80px; font-size: 12px; border: 1px solid #cadcb2; resize: none;"></textarea>
			</div>
			<input type="hidden" name="deviceId" /> <input type="submit"
				value="确  定"
				style="width: 70px; height: 28px; border: 1px solid #cadcb2; cursor: pointer;" />
		</form>
	</div>
</body>
</html>