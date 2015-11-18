<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>
<style>
  input[type="password"]{
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
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<script type="text/javascript" charset="utf-8">
            $(function(){
                $(".oldPassword").blur(function(){
                	 var password = $.trim($(this).val());
                	 if(password==""){
                		 return
                	 }
                     $.post("/user/valiatePassword",{"password":password},function(result){
                    	  if(!result){
                    		   $("span.tip").text("密码输入错误！");
                    	  }else{
                    		   $("span.tip").text("");
                    	  }
                     },"json");
                });
            });
            
            function validate(){
                var newPW1 = $.trim($(".newPassword1").val());
                var newPW2 = $.trim($(".newPassword2").val());
                if(newPW1==""){
                    alert("请输入新密码!");
                    return false;
                }else if(newPW1!=newPW2){
                    alert("两次密码输入不一致!");
                    return false;
                }else if($.trim($(".oldPassword").val())==""||$.trim($("span.tip").text())!=""){
                	alert("请输入正确的原始密码!");
                	return false;
                }
                return true;
            }
</script>
</head>
<body>
<form method="post" action="/user/updatePassword" onsubmit="return validate()">
<table width="100%" height="426" border="0" align="center" cellpadding="0" cellspacing="0" style="font-size:15px;">
  <tr>
    <td height="101" align="center"><h1> 修改用户密码</h1></td>
  </tr>
  <tr align="center" valign="middle">
    <td height="270"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="30" align="center" valign="middle">
        <c:if test="${requestScope.info!=null}">
        <span>${requestScope.info}</span>
        </c:if>
        </td>
      </tr>
      <tr>
        <td height="235" align="center" valign="middle"><table width="50%" border="0" cellpadding="0" cellspacing="5">
          <tr>
            <td width="30%" height="59" align="right" valign="middle">原密码：</td>
            <td width="70%" align="left" valign="middle"><input type="password" class="oldPassword" name="oldPassword"/>&nbsp;&nbsp;<span class="tip"></span></td>
          </tr>
          <tr>
            <td height="58" align="right" valign="middle">请输入新密码：</td>
            <td align="left" valign="middle"><input type="password" class="newPassword1" name="newPassword1"/></td>
          </tr>
          <tr>
            <td height="48" align="right" valign="middle">再次输入密码：</td>
            <td align="left" valign="middle"><input type="password" class="newPassword2" name="newPassword2"/></td>
          </tr>
        </table></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td height="46" align="center" valign="middle"><input type="submit" value="修  改" style="width:80px;height:28px;
    cursor:pointer;border:1px solid #cadcb2"/></td>
  </tr>
</table>
</form>
</body>
</html>

