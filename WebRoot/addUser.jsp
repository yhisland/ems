<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户注册</title>
<style>
    strong{
        color: red;
    }
    
    span{
      display:inline-block;
	  color:red;
	  font-size:12px;
	  text-align:center;
    }
	
	input[type="text"],input[type="password"]{
	    display:inline-block;
	    width:260px;
	    height:30px;
	    line-height:28px;
	    border:1px solid #cadcb2;
	}
</style>
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="/js/ems.js"></script>
<script type="text/javascript" charset="utf-8">
             $(function(){
                 $(".username").blur(isUserNameExist);
             });
             
             //提交表单时，判断用户信息是否填写完整
             function validate(){
                 var username = $(".username").val();
                 var password = $(".password").val();
                 var realName = $(".name").val();
                 var telNumber =$(".telNumber").val();
                 if(username==""||password==""||realName==""||telNumber==""){
                     alert("注册信息未填写完整！");
                     return false;
                 }else if($("span.tip").text()!=""){
                	 alert("请输入合法的用户名!");
                     return false;
                 }else if($("span.validate").text()!=""){
                	 alert("请输入合法的联系电话!");
                     return false;
                 }
                 return true;
             }
             
             //发送ajax请求判断用户名是否存在
             function isUserNameExist(){
            	 var username = $(".username").val();
            	 if(username==""){
            		 return;
            	 }
                 $.post("/user/validateUserName",{"username":username},function(result){
                	  if(!result){
                		   $("span.tip").text("用户名已存在!");
                	  }else{
                		   $("span.tip").text("");
                	  }
                 },"json");
             }
             
             function validateTelNumber(input){
            	 var telNumber = $.trim($(input).val());
            	 if(Ems.validateTelNumber(telNumber)){
            		 $("span.validate").text("");
            	 }else{
            		 $("span.validate").text("号码不合法!");
            	 }
             }
</script>
</head>
<body>
<form action="/user/addUser" method="post" onsubmit="return validate()">
<table width="100%" border="0" cellspacing="0" cellpadding="0" style="font-size:15px;">
  <tr>
    <td height="80" align="center" valign="middle"><h1>添加新用户</h1></td>
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
        <td width="27%" height="51" align="right" valign="middle">用户名：</td>
        <td width="73%"><input type="text" class="username" name="user.username"/>&nbsp;<strong>*</strong><span class="tip"></span></td>
      </tr>
      <tr>
        <td height="48" align="right" valign="middle">密  码：</td>
        <td><input type="password" class="password" name="user.password" />&nbsp;<strong>*</strong></td>
      </tr>
      <tr>
        <td height="50" align="right" valign="middle">真实姓名：</td>
        <td><input type="text" class="name" name="user.name"/>&nbsp;<strong>*</strong></td>
      </tr>
      <tr>
        <td height="54" align="right" valign="middle">用户类型：</td>
        <td>
          <input type="radio" class="userType" name="user.userType" value="0"/>管理员&nbsp;&nbsp;&nbsp;
          <input type="radio" class="userType" name="user.userType" value="1" checked="checked"/>教师&nbsp;&nbsp;&nbsp;
          <input type="radio" class="userType" name="user.userType" value="2"/>学生
        </td>
      </tr>
      <tr>
        <td height="50" align="right" valign="middle">联系电话：</td>
        <td><input type="text" class="telNumber" name="user.telNumber" onblur="validateTelNumber(this)"/>&nbsp;<strong>*</strong><span class="validate"></span></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="50" align="center" valign="middle"><input type="submit" value="注  册" style="width:80px;height:28px;
    cursor:pointer;border:1px solid #cadcb2"/></td>
  </tr>
</table> 
</form>
</body>
</html>
