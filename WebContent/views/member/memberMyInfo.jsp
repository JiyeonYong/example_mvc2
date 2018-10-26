<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "kr.or.iei.member.model.vo.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>마이페이지</title>
</head>
<body>
	<%
		Member m = (Member)request.getAttribute("member");
		
	%>
	
	<center>
	<fieldset style = "width:300px">
		<legend>회원 정보</legend>
		<form align = "left" action = "/infoUpdate.do" method = "post">
		ID : <%=m.getUserId() %><br>
		<input type = "hidden" name = "userId" \value = "<%=m.getUserId() %>" />
		PW : <input type = "password" name = "userPwd"  value = "<%=m.getUserPwd() %>" /><br>
		PW_RE : <input type = "password" value = "<%=m.getUserPwd() %>" /><br>
		NAME : <%=m.getUserName() %><br>
		AGE :<%=m.getAge() %><br>
		EMAIL : <input type = "email" name = "email"  value = "<%=m.getEmail() %>" /><br>
		PHONE : <input type = "phone" name = "phone"  value = "<%=m.getPhone() %>" /><br>
		ADDRESS : <input type = "text" name = "address"  value = "<%=m.getAddress() %>" /><br>
		GENDER : <% if(m.getGender() == 'M'){%> 남
				 <% } else { %> 여 <%} %><br>
		HOBBY : <input type = "text" name = "hobby"  value = "<%=m.getHobby() %>" /><br>
		
		<input type = "submit" value = "수정하기" />
		<input type = "button" value = "취소" onclick = "return back();"/>
		</form>
	</fieldset>
	</center>
	
	<script>
		function back() {
			history.go(-1);
			return false;
		}
	</script>
</body>
</html>