<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="table.css">
</head>
<body>
	<center>
	<h3>사원 목록</h3>
	<table id="table_content" width=700>
		<tr>
		<td align="left>
		<a href="graph.do">그래프로 통계보기</a>
		</td>
		</tr>
	
	
	<table id="table_content" width=700>
		<tr>
			<th>사번</th>
			<th>이름</th>
			<th>직위</th>
			<th>입사일</th>
			<th>급여</th>
			<th>부서</th>
		</tr>
		<c:forEach var="vo" items="${list }">
			<tr>
			<td>${vo.empno }</td>
			<td>${vo.ename }</td>
			<td>${vo.job }</td>
			<td><fmt:formatDate value="${vo.hiredate }" pattern="yyy-MM-dd"/></td>
			<td>${vo.sal }</td>
			<td>${vo.deptno }</td>
			</tr>
		</c:forEach>
	</table>
		<img src="sal.png">
	</center>
</body>
</html>