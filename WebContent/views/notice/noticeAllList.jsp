<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "kr.or.iei.notice.model.vo.*"
    import = "java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지 사항</title>
</head>
<body>
	<%
		ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("noticeList");
	%>
	
	<center>
	<h1>공지사항</h1>
	<table border="1">
		<tr>
			<td>글번호</td><td>글제목</td><td>글쓴이</td><td>작성일</td>
		</tr>
		
		<% for(Notice n : list) {%>
			<tr>
				<td><%=n.getNoticeNo()%></td>
				<td><a href= "/notice.do?noticeNo=<%=n.getNoticeNo()%>"><%=n.getSubject()%></a></td>
				<td><%=n.getUserId()%></td>
				<td><%=n.getRegDate()%></td>
			</tr>	
		<%} %>
	</table>
	</center>
</body>
</html>