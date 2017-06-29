<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="table.css">
</head>
<body>
<!-- /home/sist/springDev/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SpringMongoDBProject/ -->
	<center>
		<h3>게시판</h3>
		<table id="table_content" width=700>
		<tr>
			<td align=left>
				<a href="insert.do">새글</a>
			</td>
		</tr>
		</table>
		<table id="table_content" width=700>
		<tr>
			<th width=10%>번호</th>
			<th width=45%>제목</th>
			<th width=15%>이름</th>
			<th width=20%>작성일</th>
			<th width=10%>조회수</th>
		</tr>
		<c:forEach var="vo" items="${list }">
		<tr>
			<td width=10% align=center>${vo.no }</td>
			<td width=45% align=left>
			<a href="content.do?no=${vo.no}">${vo.subject }</a>
			</td>
			<td width=15% align=center>${vo.name }</td>
			<td width=20% align=center>${vo.regdate }</td>
			<td width=10% align=center>${vo.hit }</td>
		</tr>
		</c:forEach>
		
		</table>
	</center>

</body>
</html>