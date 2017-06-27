<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html"; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="table.css">
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});
	google.charts.setOnLoadCallback(drawChart);

	function drawChart() {

		var data = google.visualization.arrayToDataTable([
				[ '영화명', '예매율' ],
				<c:forEach var="vo" items="${list}">
				[ '<c:out value="${vo.title}"/>', <c:out value="${vo.reserve}"/>],
				</c:forEach>
		]);

		var options = {
			title : '영화별 예매현황',
			is3D : true
		};

		var chart = new google.visualization.PieChart(document.getElementById('piechart'));

		chart.draw(data, options);
	}
</script>

</head>
<body>
	<center>
		<table id="table_content" width=900>
			<tr>
				<c:forEach var="vo" items="${list }">
					<td>
					<a href="detail.do?mno=${vo.mno }">
					<img src="${vo.poster}" width=100 height=150>
					</a>
					</td>
				</c:forEach>
			</tr>
			<tr>
				<c:forEach var="vo" items="${list }">
					<th>${vo.title}</th>
				</c:forEach>
			</tr>
			<tr>
				<c:forEach var="vo" items="${list }">
					<td>${vo.regdate}</td>
				</c:forEach>
			</tr>
		</table>
		<div id="piechart" style="width: 900px; height: 500px;"></div>
	</center>
</body>
</html>