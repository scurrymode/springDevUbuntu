<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery.js"></script>
<script>
$(function(){
	setInterval(showGraph, 2000);
});

function showGraph(){
	$.ajax({
		type:"post",
		url:"graph.do",
		success:function(response){
			$('#graph').html(response);
		}
	});
}
</script>
</head>
<body>
	<center>
		<div id="graph"></div>
	</center>
</body>
</html>