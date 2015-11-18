<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>批量添加</title>
<style>
span {
	display: inline-block;
	color: red;
	font-size: 12px;
	text-align: center;
}
input{ vertical-align:middle; margin:0; padding:0}
div.file-box{ position:relative;width:340px}
input.txt{ height:30px; border:1px solid #cadcb2; width:240px;}
input.btn{ background-color:#FFF; border:1px solid #cadcb2;height:32px; width:70px;}
input.file{ position:absolute; top:0; left:5px; height:30px; filter:alpha(opacity:0);opacity: 0;width:320px }
</style>
</head>
<body>
	<form action="${requestScope.handleUrl}" method="post"
		enctype="multipart/form-data">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="font-size: 15px;">
			<tr>
				<td height="80" align="center" valign="middle"><h1>${requestScope.hInfo}</h1></td>
			</tr>
			<tr>
				<td height="43" align="center" valign="middle"><c:if
						test="${requestScope.info!=null}">
						<span>${requestScope.info}</span>
					</c:if></td>
			</tr>
			<tr>
				<td height="154" align="center" valign="middle"><table
						width="50%" border="0" cellspacing="5" cellpadding="0">
						<tr>
							<td width="27%" height="47" align="right" valign="middle">模板文件：</td>
							<td width="73%">
								<div class="file-box">
									<input type='text' name='textfield' id='textfield' class='txt' />
									<input type='button' class='btn' value='浏  览'/> 
									<input
										type="file" name="templateFile" class="file" id="templateFile"
										size="28"
										onchange="document.getElementById('textfield').value=this.value" />
								</div>
							</td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td height="50" align="center" valign="middle"><input
					type="submit" value="添  加"
					style="width: 80px; height: 28px; cursor: pointer; border: 1px solid #cadcb2" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
