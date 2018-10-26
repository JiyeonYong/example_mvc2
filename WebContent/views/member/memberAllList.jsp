<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "kr.or.iei.member.model.vo.*"
    import = "java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원 목록</title>
</head>
<body>
	<%
		ArrayList<Member> list =  (ArrayList<Member>)request.getAttribute("memberList");
		
	%>
	
	<h1>관리자 페이지</h1>
	
	<table border = "1">
		<tr><th>ID</th><th>PW</th><th>NAME</th><th>AGE</th><th>EMAIL</th><th>PHONE</th>
		<th>ADDRESS</th><th>GENDER</th><th>HOBBY</th<th>ACTIVE</th><th>ENROLLDATE</th>></tr>
		
		<%for(Member m : list){%>
			<tr>
				<td><%=m.getUserId() %></td>
				<td><%=m.getUserPwd() %></td>
				<td><%=m.getUserName() %></td>
				<td><%=m.getAge() %></td>
				<td><%=m.getEmail() %></td>
				<td><%=m.getPhone() %></td>
				<td><%if(m.getGender() == 'M'){%>
						남
					<%} else {%>
						여
					<%} %>
				</td>
				<td><%=m.getHobby() %></td>
				<td><%if(m.getActive() == 'Y'){%>
						사용
					<%} else {%>
						탈퇴
					<%} %></td>
				<td><%=m.getEnrollDate() %></td>
			</tr>		
		<%}%>
		
	</table>
		<br>
		<button onclick ="history.go(-1);">뒤로가기</button>
		
</body>
</html>