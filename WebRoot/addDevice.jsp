<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加新设备</title>
<style>
 input[type="text"]{
	  display:inline-block;
	  width:260px;
	  height:30px;
	  line-height:28px;
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
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/js/ems.js"></script>
<script type="text/javascript">
  function validate(){
	  var equipNumber = $(".equipNumber").val();
	  var equipName = $(".equipName").val();
	  var equipModel = $(".equipModel").val();
	  var price = $(".price").val();
	  if(equipNumber==""||equipName==""||equipModel==""||price==""){
		  alert("请输入完整的设备信息!");
		  return false;
	  }else if($("span.validate").text()!=""){
		  alert("请输入合法的价格!");
		  return false;
	  }else if($("span.tip").text()!=""){
		  alert("请输入正确的设备编号!");
		  return false;
	  }
	  return true;
  }
  
  function validatePrice(input){
	  var price = $.trim($(input).val());
	  if(Ems.validateNumber(price)){
		  $("span.validate").text("");
	  }else{
		  $("span.validate").text("价格不合法!");
	  }
  }
  
  //发送ajax请求判断设备编号是否存在
  function isDeviceExist(input){
 	 var equipNumber = $(input).val();
 	 if(equipNumber==""){
 		 return;
 	 }
      $.post("/device/validateDeviceExist",{"equipNumber":equipNumber},function(result){
     	  if(!result){
     		   $("span.tip").text("设备编号已存在!");
     	  }else{
     		   $("span.tip").text("");
     	  }
      },"json");
  }
</script>
</head>

<body>
<form action="/device/addDevice" method="post" onsubmit="return validate()">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:15px;">
  <tr>
    <td height="80" align="center" valign="middle"><h1>添加设备</h1></td>
  </tr>
  <tr>
    <td height="43" align="center" valign="middle">
    <c:if test="${requestScope.info!=null}">
      <span>${requestScope.info}</span>
    </c:if>
    </td>
  </tr>
  <tr>
    <td height="304" align="center" valign="middle"><table width="50%" border="0" cellspacing="5" cellpadding="0">
      <tr>
        <td width="27%" height="48" align="right" valign="middle">设备编号：</td>
        <td width="73%"><input type="text" name="device.equipNumber" class="equipNumber" onblur="javascript:isDeviceExist(this);"/>
        <span class="tip"></span>
        </td>
      </tr>
      <tr>
        <td height="50" align="right" valign="middle">设备名称：</td>
        <td><input type="text" name="device.equipName" class="equipName"/></td>
      </tr>
      <tr>
        <td height="51" align="right" valign="middle">设备型号：</td>
        <td><input type="text" name="device.equipModel" class="equipModel"/></td>
      </tr>
      <tr>
        <td height="49" align="right" valign="middle">设备单价：</td>
        <td><input type="text" name="device.price" class="price" onblur="validatePrice(this)"/>&nbsp;<span class="validate"></span></td>
      </tr>
      <tr>
        <td height="51" align="right" valign="middle">购买时间：</td>
        <td><input class="Wdate" type="text" name = "device.buyDate" onclick="WdatePicker()"/></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="50" align="center" valign="middle"><input type="submit" value="新  增" style="width:80px;height:28px;
    cursor:pointer;border:1px solid #cadcb2"/></td>
  </tr>
</table>
</form>
</body>
</html>
