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
	<%=application.getRealPath("/") %>
	<center>
		<table id="table_content" width=500>
			<tr>
				<td colspan=2 align=center><embed src="${vo.link }" width=600 height=400></td>
			</tr>
			<tr>
				<td width=15% align=right>노래명</td>
				<td width=85%>${vo.title }</td>
			</tr>
			<tr>
				<td width=15% align=right>가수명</td>
				<td width=85%>${vo.singer }</td>
			</tr>
			<tr>
				<td width=15% align=right>랭크</td>
				<td width=85%>${vo.rank }</td>
			</tr>
		</table>
		<table id="table_content" width=600>
			<tr>
				<td align=center><img src="barchart.png"></td>
				<td align=center><img src="wordcloud.png"></td>
			</tr>
		</table>
	</center>
</body>
</html>