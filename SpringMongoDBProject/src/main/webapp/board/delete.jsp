<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="table.css">
</head>
<body>
	<center>
		<h3>삭제하기</h3>
		<form method=post action=delete_ok.do>
		<table id="table_content" width=300>
		<tr>
		<th width=30%>비밀번호</th>
		<td width=70%>
		<input type=password name=pwd size=10>
		<input type="hidden" name="no" value=${no }>
		</td>
		</tr>
		<tr>
		<td colspan=2 align=center>
		<input type=submit value=삭제>
		<input type=button value=취소 onclick="javascript:history.back()">
		</td>
		</tr>
		</table>
		</form>
	</center>
</body>
</html>