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
	<h1>用户信息列表</h1>
	<table>
		<thead>
			<tr>
				<th><input
					onClick="if(this.checked==true) { checkAll('test'); } else { clearAll('test'); }"
					type="checkbox" value="" name="test" title="全选/取消" /></th>
				<th>用户ID</th>
				<th>用户名</th>
				<th>用户姓名</th>
				<th>用户类型</th>
				<th>联系电话</th>
				<th>用户操作</th>
			</tr>
		</thead>
		<tbody id="tab">
			<c:forEach items="${requestScope.userList}" var="user">
				<tr>
					<td><input type="checkbox" value="${user.id}" name="test" /></td>
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.name}</td>
					<td>${user.userTypeText}</td>
					<td>${user.telNumber}</td>
					<td>&nbsp;&nbsp;<a href="/user/deleteUser/${user.id}">删除</a>
						&nbsp;&nbsp;<a href="/user/editUser/${user.id}">修改</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td>
				<img src="/images/delete.ico" title="删除选中项"
					style="cursor: pointer;"
					onclick="javascript:deleteSelected('/user/deleteAllSelected','/user/queryPageUser?offset=1')" />
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
</body>
</html>