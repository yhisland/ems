<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加维修记录</title>
<style>
  input[type="text"],textarea,select{
      width:260px;
	  border:1px solid #cadcb2;
  }
  span{
	  display:inline-block;
	  color:red;
	  font-size:12px;
	  text-align:center;
  }
</style>
<script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<form action="/maintain/addMaintain" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:15px;">
  <tr>
    <td height="80" align="center" valign="middle"><h1>添加维修记录</h1></td>
  </tr>
  <tr>
    <td height="43" align="center" valign="middle">
    <c:if test="${requestScope.info!=null}">
      <span>${requestScope.info}</span>
    </c:if>
    </td>
  </tr>
  <tr>
    <td height="285" align="center" valign="middle"><table width="50%" border="0" cellspacing="5" cellpadding="0">
      <tr>
        <td width="27%" height="48" align="right" valign="middle">设备编号：</td>
        <td width="73%"><select name="maintain.equipId" style="height:30px">
           <c:forEach items="${requestScope.devices}" var="device">
				 <option value="${device.id}">${device.equipNumber}</option>
		   </c:forEach>
        </select></td>
      </tr>
      <tr>
        <td height="174" align="right" valign="middle">维护描述：</td>
        <td><textarea name="maintain.maintainText" style="height:200px;overflow-x：hidden;overflow-y：auto;"></textarea></td>
      </tr>
      <tr>
        <td height="47" align="right" valign="middle">維修时间：</td>
        <td><input class="Wdate" type="text" name="maintain.maintainTime" onclick="WdatePicker()" style="height:30px"/></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="50" align="center" valign="middle"><input type="submit" value="添  加" style="width:80px;height:28px;
    cursor:pointer;border:1px solid #cadcb2"/></td>
  </tr>
</table>
</form>
</body>
</html>
