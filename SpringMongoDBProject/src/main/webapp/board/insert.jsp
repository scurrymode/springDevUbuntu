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
		<h3>글쓰기</h3>
		<form method=post action=insert_ok.do>
		<table id="table_content" width=600>
		<tr>
		<th width=20%>이름</th>
		<td width=80%>
		<input type="text" name="name" size=12>
		</td>
		</tr>
		<tr>
		<th width=20%>제목</th>
		<td width=80%>
		<input type="text" name=subject size=30>
		</td>
		</tr>
		<tr>
		<th width=20%>내용</th>
		<td width=80%>
		<textarea rows=10 cols=55 name=content></textarea>
		</td>
		</tr>
		<tr>
		<th width=20%>비밀번호</th>
		<td width=80%>
		<input type=password name=pwd size=10>
		</td>
		</tr>
		<tr>
		<td colspan=2 align=center>
		<input type=submit value=글쓰기>
		<input type=button value=취소 onclick="javascript:history.back()">
		</td>
		</tr>
		</table>
		</form>
	</center>
</body>
</html>