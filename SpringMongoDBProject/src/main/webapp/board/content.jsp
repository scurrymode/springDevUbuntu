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
	<h3>내용보기</h3>
	<table id="table_content" width=600>
	 <tr>
	 <th width=20%>번호</th>
    <td width=30% align=center>${vo.no }</td>
	 <th width=20%>작성일</th>
	 <td width=30% align=center>${vo.regdate }</td>
	 </tr>
	 <tr>
	 <th width=20%>이름</th>
    <td width=30% align=center>${vo.name }</td>
	 <th width=20%>조회수</th>
	 <td width=30% align=center>${vo.hit }</td>
	 </tr>
	 <tr>
	 <th width=20%>제목</th>
	 <td colspan=3 align=left>${vo.subject }</td>
	 </tr>
	 <tr>
	 <td colspan=4 valign="top" height=100>${vo.content }</td>
	 </tr>
	 </table>
	<table id=table_content width=600>
	<tr>
	<td align=right>
		<a href="update.do?no=${vo.no }">수정</a>&nbsp;
		<a href="delete.do?no=${vo.no }">삭제</a>&nbsp;
		<a href="list.do">목록</a>
	</td>
	</tr>
	</table>
	
	<table id=table_content width=600>
	<tr>
	<td align=center>
	<img src="result.png">
	</td>
	</tr>
	</table>
	
	
	</center>

</body>
</html>