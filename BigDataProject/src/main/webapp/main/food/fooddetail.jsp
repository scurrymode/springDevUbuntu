<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="style/table.css">
<link rel="stylesheet" href="style/style.css">
</head>
<body>
	<div id="news_area">
		<!--news area-->
		<center>
			<table id="table_content" width=700>
				<tr>
					<td width=40% align=center rowspan="5"><img
						src="${vo.poster }" width=280 height=300></td>
					<td colspan="2" align=center><h2>${vo.name }&nbsp;&nbsp;${vo.score }</h2></td>
				</tr>
				<tr>
					<td width=10% align=right>주소</td>
					<td width=50% align=left>${vo.address }</td>
				</tr>
				<tr>
					<td width=10% align=right>전화</td>
					<td width=50% align=left>${vo.tel }</td>
				</tr>
				<tr>
					<td width=10% align=right>음식종류</td>
					<td width=50% align=left>${vo.type }</td>
				</tr>
				<tr>
					<td width=10% align=right>가격대</td>
					<td width=50% align=left>${vo.price }</td>
				</tr>

			</table>
			<script src="https://www.amcharts.com/lib/3/amcharts.js"></script>
			<script src="https://www.amcharts.com/lib/3/pie.js"></script>
			<script src="https://www.amcharts.com/lib/3/themes/light.js"></script>
			<div id="chartdiv"></div>
			<script src='https://code.jquery.com/jquery-1.11.2.min.js'></script>

			<script type="text/javascript">
				var chart = AmCharts.makeChart("chartdiv", {
					"type" : "pie",
					"theme" : "light",
					"dataProvider" : <%=request.getAttribute("json")%>,
					"valueField" : "count",
					"titleField" : "taste",
					"balloon" : {
						"fixedPosition" : true
					},
					"export" : {
						"enabled" : true
					}
				});
			</script>
		</center>
		<div class="clr"></div>

	</div>
	<!--news area end-->
</body>
</html>




