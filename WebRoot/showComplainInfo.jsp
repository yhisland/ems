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
	<h1>投诉信息列表</h1>
	<table>
		<thead>
			<tr>
				<th><input
					onClick="if(this.checked==true) { checkAll('test'); } else { clearAll('test'); }"
					type="checkbox" value="" name="test" title="全选/取消" /></th>
				<th>流水号</th>
				<th>受理时间</th>
				<th>受理号码</th>
				<th>联系电话</th>
				<th>用户姓名</th>
				<th>业务内容</th>
				<th>工单标题</th>
				<th>宽带装机地址</th>
				<th>区域</th>
				<th>小区</th>
				<th>区域负责人</th>
				<th>维护员</th>
				<th>故障原因</th>
				<th>故障详细</th>
			</tr>
		</thead>
		<tbody id="tab">
			<c:forEach items="${requestScope.complainList}" var="complain"
				varStatus="status">
				<tr>
					<td><input type="checkbox" value="${complain.id}" name="test" /></td>
					<td>${complain.serialNum}</td>
					<td>${complain.acceptTime}</td>
					<td>${complain.complainPhone}</td>
					<td>${complain.contactPhone}</td>
					<td>${complain.userName}</td>
					<td>${complain.content}</td>
					<td>${complain.title}</td>
					<td>${complain.site}</td>
					<td>${complain.district}</td>
					<td>${complain.community}</td>
					<td>${complain.responsible}</td>
					<td>${complain.worker}</td>
					<td>${complain.faultCause}</td>
					<td>${complain.faultDetail}</td>
					
<%-- 					<td class="state${status.index}">${complain.currentState}</td> --%>
<%-- 					<c:if test="${complain.currentState=='预约'}">
						<script type="text/javascript">
							$("td.state${status.index}").css("color", "red");
						</script>
					</c:if> --%>
<%-- 					<td><c:if test="${sessionScope.user.get('userType')==0}">
					&nbsp;&nbsp;<a href="/complain/deleteComplain/${complain.id}"
								onclick="return window.confirm('您确定要删除?');">删除</a>
					&nbsp;&nbsp;<a href="/complain/edit/${complain.id}">修改</a>
							<c:if test="${complain.currentState=='借出'}">
					&nbsp;&nbsp;<a href="/complain/back/${complain.id}">归还</a>
							</c:if>
						</c:if> <c:if
							test="${complain.currentState=='在库'&&sessionScope.user.get('userType')!=0&&complain.currentSituation=='正常'}">
					&nbsp;&nbsp;<a href="#subscribeDateBox" class="modaltrigger" name="${complain.id}">预约</a>
						</c:if> <c:if
							test="${complain.currentState=='预约'&& sessionScope.user.get('userType')==0}">
					&nbsp;&nbsp;<a href="/complain/audit/${complain.id}">审核</a>
						</c:if> <c:if test="${sessionScope.user.get('userType')!=2}">
				    &nbsp;&nbsp;<a href="/maintain/queryPageMaintain/${complain.id}-1">维修记录</a>
						</c:if></td> --%>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
<%-- 				<td><c:choose>
						<c:when test="${sessionScope.user.get('userType')==0}">
							<img src="/images/delete.ico" title="删除选中项"
								style="cursor: pointer;"
								onclick="javascript:deleteSelected('/complain/deleteAllSelected','/complain/queryPageComplain?offset=1')" />
						</c:when>
						<c:otherwise>
							<input
								onClick="if(this.checked==true) { checkAll('test'); } else { clearAll('test'); }"
								type="checkbox" value="" name="test" title="全选/取消" />
						</c:otherwise>
					</c:choose></td> --%>
				<td>
					<form method="post" action="/complain/queryComplainByLike"
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
		<form action="/complain/subscribe" method="post"
			style="text-align: center; font-size: 13px;">
			<div class="div1">
				预约备注：
				<textarea name="subscribeNote"
					style="width: 200px; height: 80px; font-size: 12px; border: 1px solid #cadcb2; resize: none;"></textarea>
			</div>
			<input type="hidden" name="complainId" /> <input type="submit"
				value="确  定"
				style="width: 70px; height: 28px; border: 1px solid #cadcb2; cursor: pointer;" />
		</form>
	</div>
</body>
</html>