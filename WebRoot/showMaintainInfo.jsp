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
<link rel="stylesheet" href="/css/table.css" type="text/css"
	media="screen" title="no title" charset="utf-8" />
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/js/jquery-pagination.js"></script>
<script type="text/javascript" src="/js/table_js.js"></script>
</head>
<body>
	<h1>维修记录列表</h1>
	<table>
		<thead>
			<tr>
				<th><input
					onClick="if(this.checked==true) { checkAll('test'); } else { clearAll('test'); }"
					type="checkbox" value="" name="test" title="全选/取消" /></th>
				<th>维修记录ID</th>
				<th>维护描述</th>
				<th>维修时间</th>
				<th>维修操作</th>
			</tr>
		</thead>
		<tbody id="tab">
			<c:forEach items="${requestScope.maintainList}" var="maintain">
				<tr>
					<td><input type="checkbox" value="${maintain.id}" name="test" /></td>
					<td>${maintain.id}</td>
					<td>${maintain.maintainText}</td>
					<td>${maintain.maintainTime}</td>
					<td><c:if test="${sessionScope.user.get('userType')==0}">
				       &nbsp;&nbsp;<a
								href="/maintain/deleteMaintain/${requestScope.deviceId}-${maintain.id}">删除</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<c:choose>
					<c:when test="${sessionScope.user.get('userType')==0}">
						<td><img src="/images/delete.ico" title="删除选中项"
							style="cursor: pointer;"
							onclick="javascript:deleteSelected('/maintain/deleteAllSelected','/maintain/queryPageMaintain/${requestScope.deviceId}-1')" />
						</td>
						<td colspan="6">
					</c:when>
					<c:otherwise>
						<td colspan="7">
					</c:otherwise>
				</c:choose>
				<div class="mypage">
					<script type="text/javascript">
						$(".mypage")
								.pager(
										{
											nTotal : "${requestScope.num}",
											nTheme : 1,
											nPageSize : 15,
											nWidth : 24,
											sUrl : "/maintain/queryPageMaintain",
											sPrevFlag : "<上一页",
                                                sNextFlag:"下一页>",
										});
					</script>
				</div>
				</td>
			</tr>
		</tfoot>
	</table>
</body>
</html>