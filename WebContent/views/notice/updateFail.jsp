<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지사항 수정</title>
</head>
<body>
	<%
		String noticeNo = request.getParameter("noticeNo");
	%>

	<script>
		var noticeNo = "<%=noticeNo%>";
		alert("실패 : 작성자만 수정할 수 있습니다.");
		location.href= "/notice.do?noticeNo="+noticeNo;
		
		
	</script>
</body>
</html>