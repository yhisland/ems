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
	<h1>系统操作日志</h1>
	<table>
		<thead>
			<tr>
				<th><input
					onClick="if(this.checked==true) { checkAll('test'); } else { clearAll('test'); }"
					type="checkbox" value="" name="test" title="全选/取消" /></th>
				<th>操作内容</th>
				<th>操作时间</th>
				<th>操作人</th>
				<th>IP地址</th>
				<th>基本操作</th>
			</tr>
		</thead>
		<tbody id="tab">
			<c:forEach items="${requestScope.logList}" var="log">
				<tr>
					<td><input type="checkbox" value="${log.id}" name="test" /></td>
					<td>${log.logContent}</td>
					<td>${log.logDate}</td>
					<td>${log.userInfo}</td>
					<td>${log.ip}</td>
					<td><c:if test="${sessionScope.user.get('userType')==0}">
				       &nbsp;&nbsp;<a href="/log/deleteLog/${log.id}">删除</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td>
				<img src="/images/delete.ico" title="删除选中项"
					style="cursor: pointer;"
					onclick="javascript:deleteSelected('/log/deleteAllSelected','/log/queryPageLog?offset=1')" />
				</td>
				<td colspan="6">
					<div class="mypage">
						<script type="text/javascript">
							$(".mypage")
									.pager(
											{
												nTotal : "${requestScope.num}",
												nTheme : 1,
												nPageSize : 15,
												nWidth : 24,
												sUrl : "/log/queryPageLog",
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