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
	<form action = "/login.do" method = "post">
		ID : <input type = "text" placeholder = "아이디 입력" name = "userId"/><br>
		PW : <input type = "password" placeholder = "비밀번호 입력" name = "userPwd"/><br>
		<input type = "hidden" name = "popup" value = "1"/>
		<input type = "submit" value = "로그인"/>
		<input type = "reset" value = "취소"/>
	</form>
	</center>
</body>
</html>