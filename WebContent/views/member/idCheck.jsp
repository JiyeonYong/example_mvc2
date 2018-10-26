<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import= "kr.or.iei.member.model.service.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>아이디 중복 확인</title>
</head>
<body>
	<%
		String checkId = request.getParameter("checkId");

		
		boolean result = new MemberService().checkId(checkId);
		
	%>	
		<script>
			var userId = '<%=checkId%>';
		
		<%  
			if(result) {
		%>
			//참일때는 해당 ID 중복
			window.onload = function() {
				var message = document.getElementById('message');
				message.innerHTML = '해당 아이디는 사용중입니다.';
				message.style.color = 'red';
				userId = "";
				opener.document.getElementById('checkFlag').value = 0;
			}
			
		<%	}else { %>
			// 해당 ID가 중복이 아닐 때
			window.onload = function() {
				var message = document.getElementById('message');
				message.innerHTML = '해당 아이디는 사용 가능';
				message.style.color = 'blue';
				opener.document.getElementById('checkFlag').value = 1;
			}
			
		<%} %>
		
		function backBtn() {
			opener.document.getElementById('userId').value = userId;
			window.close();
		}
		
		</script>
	
	<center>
	<span id = "message"></span><br>
	<button onclick = "backBtn()">확인</button>
	</center>
	
</body>
</html>