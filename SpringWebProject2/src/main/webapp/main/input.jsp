<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
	<form method=post action=input_ok.do>
		이름:<input type=text name=name size=12>
		성별:<input type=radio name=sex value="남자" checked>남자
		<input type=radio name=sex value="여자">여자<br>
		<input type="submit" value=전송>
	</form>
	</center>
</body>
</html>