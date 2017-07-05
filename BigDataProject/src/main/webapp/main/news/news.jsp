<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="style/table.css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<div id="news_area" align=center>
		<table id="table_content" width=700 >
			<tr>
				<td align=left>
				<form method=post action="news.do">
					검색
					<input type="text" name=data size=15>
					<input type="submit" value="검색">
					</form>
					</td>
			</tr>
		</table>
		<table id="table_content" width=700>
			<c:forEach var ="vo" items="${list }">
				<tr>
					<th>${vo.title }</th>
				</tr>
				<tr class="dataTr">
					<td><a href="${vo.link}">${vo.description }</a></td>
				</tr>
			</c:forEach>
		</table>
            <div class="clr"></div>                
    </div><!--news area end-->
</body>
</html>