<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="table.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
<%-- <%= application.getRealPath("/"); %> --%>
	<center>
	<table id="table_content" width=900>
	<tr>
	<td colspan=2>
	<form method="post" action="news_list.do">
		Search:<input type=text name=data size=15 value="${data}">
		<input type=submit value="검색">
	</form>
	</td>
	</tr>
	<tr>
	<td width=450>
		<table id=table_content width=450>
			<c:forEach var="vo" items="${list }">
			<tr><th>${vo.title }</th></tr>
			<tr><td>
			<a href="${vo.link}" target="_blank">${vo.description }</a>
			</td></tr>
			</c:forEach>	
		</table>
		<table id=table_content width=450>
			<tr>
			<td align=right>
				<a href="news_list.do?data=${data }&page=${page>1?page-1:page}">이전</a>&nbsp;
				<a href="news_list.do?data=${data }&page=${page<5?page+1:page}">다음</a>&nbsp; &nbsp;
				${page } page / 5 pages
			</td>
			</tr>
		</table>
	</td>
	<td width=450>
		<table id=table_content width=450>
			<tr>
			<td align=center>
				<img src="news.png" width=450 height=450>
			</td>
			</tr>
		</table>
	
	</td>
	</tr>

	</table>
	<jsp:include page="wordcloud.jsp"></jsp:include>
	</center>
</body>
</html>