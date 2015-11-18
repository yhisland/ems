<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审核设备申请</title>
<style>
   input[type="text"]{
	  display:inline-block;
	  width:260px;
	  height:30px;
	  line-height:28px;
	  border:1px solid #cadcb2;
	  text-align:center;
 }
</style>
<script type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<form action="/device/completeDevice" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="80" align="center" valign="middle"><h1>审核设备申请单</h1></td>
  </tr>
  <tr>
    <td height="295" align="center" valign="middle"><table width="50%" border="0" cellspacing="5" cellpadding="0">
      <tr>
        <td width="27%" height="46" align="right">设备名称：</td>
        <td width="73%"><input type="text" name="equipName" value="${device.equipName}" disabled="disabled"/></td>
      </tr>
      <tr>
        <td height="49" align="right">设备编号：</td>
        <td><input type="text" name="equipNumber" value="${device.equipNumber}" disabled="disabled"/></td>
      </tr>
      <tr>
        <td height="48" align="right">设备型号：</td>
        <td><input type="text" name="equipModel" value="${device.equipModel}" disabled="disabled"/></td>
      </tr>
      <tr>
        <td height="47" align="right">预约人：</td>
        <td><input type="text" name="currentUser" value="${device.currentUser}" disabled="disabled"/></td>
      </tr>
      <tr>
        <td height="47" align="right">预约备注：</td>
        <td><input type="text" name="subscribeNote" value="${device.subscribeNote}" disabled="disabled"/></td>
      </tr>
      <tr>
        <td height="48" align="right">指定归还日期：</td>
        <td><input class="Wdate" type="text" name="returnDate" onclick="WdatePicker()" /></td>
      </tr>
      <tr>
        <td height="46" align="right">审核结果：</td>
        <td>
          <input type="radio" name="result" value="true" checked="checked"/>通过&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          <input type="radio" name="result" value="false"/>不通过
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="50" align="center" valign="middle"><input type="submit" value="提  交" style="width:80px;height:28px;
    cursor:pointer;border:1px solid #cadcb2"/></td>
  </tr>
</table>
<input type="hidden" name="id" value="${device.id}"/>
</form>
</body>
</html>
