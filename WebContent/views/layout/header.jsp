<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "kr.or.iei.member.model.vo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<%if (session.getAttribute("member") == null) {%>
		<a href ="" style = "float:right" onclick = "return loginPopup();">로그인</a>
	<%} else {%>
		<a href ="/logout.do" style = "float:right">로그아웃</a>
		<a style = "float:right" ><%=((Member)session.getAttribute("member")).getUserId() %>님 환영합니다!</a>
	<%} %>
	<span id = "result" value = "0"></span>
	
</body>
</html>