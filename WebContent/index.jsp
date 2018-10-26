<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "kr.or.iei.member.model.vo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>기본 페이지</title>
</head>
<body>
	<%
		session = request.getSession(false);
		Member member = (Member)session.getAttribute("member");
		
		if(member != null){
	%>
	<h1>[<%=member.getUserName() %>] 님 환영합니다!</h1>
	<a href ="/myInfo.do">마이페이지</a>
	<a href ="/logout.do">로그아웃</a>
	
	<%if(!member.getUserId().equals("admin")) { %>
		<a href= "/mdelete.do">회원탈퇴</a>
	<%} %>
	
	
	<%if(member.getUserId().equals("admin")) { %>
		<a href ="/admin.do">관리자 페이지</a>
	<%} %>
	
	<%} else {%>
	
	<h1>WelCome!! KH 홈페이지에 오신걸 한영합니다 ♥</h1>
	
	<form action = "/login.do" method = "post">
		ID : <input type = "text" name = "userId"/><br>
		PW : <input type = "password" name = "userPwd"/><br>
		<input type = "submit" value = "로그인"/>
		<input type = "reset" value = "취소"/>
	</form>
	
	<a href= "/views/member/enroll.html">회원가입</a>
	<%} %>
	
	<a href = "/noticeList.do">공지사항</a>
	
	
	
	
</body>
</html>