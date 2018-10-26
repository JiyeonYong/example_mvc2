<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import = "kr.or.iei.notice.model.vo.*"
    import = "kr.or.iei.member.model.vo.*"
    import = "java.util.ArrayList"%>
    
    
<%
	//Controller(Servlet)에서 보내준 값 가져오기
	PageData pd = (PageData)request.getAttribute("pageData");

	ArrayList<Notice> list = pd.getList();	//현재 페이지의 글 목록
	String pageNavi = pd.getPageNavi();		//현재 navi Bar
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지사항</title>
</head>
<body>

	
	<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	
	
	
	<a href = "/index.jsp"><img src = "/img/logo_sample3.PNG" style = "width:80px; height:80px; margin :20px;""/></a>
	
	<%if (session.getAttribute("member") == null) {%>
		<a href ="" style = "float:right" onclick = "return loginPopup();">로그인</a>
	<%} else {%>
		<a href ="/logout.do" style = "float:right">로그아웃</a>
		<a style = "float:right" ><%=((Member)session.getAttribute("member")).getUserId() %>님 환영합니다!</a>
	<%} %>
	<span id = "result" value = "0"></span>
	
	<script>
		function loginPopup() {
			window.open("/views/member/loginPopup.jsp", "_blank", "width=400px, height =180px");
			return false;
		}
	</script>
	
	<center>
		<h1>공지사항</h1>
		<table border = "1">
			<tr>
				<th>글번호</th><th>글제목</th><th>작성자</th><th>작성일</th>
			</tr>
			<%for(Notice n : list){%>
				<tr>
					<td><%=n.getNoticeNo() %></td>
					<td><a href= "notice.do?noticeNo=<%=n.getNoticeNo()%>"><%=n.getSubject() %></a></td>
					<td><%=n.getUserId() %></td>
					<td><%=n.getRegDate() %></td>
				</tr>
			<%}%>
		</table>
		
		<%-- <div style = "width:315px; text-align:center;">
			<label><%=pageNavi%></label>
		</div> --%>
		
		<ul class = "pagination justify-content-center" style="margin:20px">
			<%=pageNavi %>
		</ul>
		
		
		<form style = "display:inline" action = "/noticeSearch.do" method = "get">
			<input type = "text" name = "search"/>
			<input type = "submit" value = "검색"/>
		</form>
		
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		
		
		<% 
			session = request.getSession(false);
			Member m = (Member)session.getAttribute("member");
			
			if(m != null && m.getUserId().equals("admin")){
		%>
		<form style = "display:inline" action = "/views/notice/noticeWrite.jsp">
			<input type = "submit" value = "글쓰기"/>
		</form>
		
		<%} %>
		
		
	</center>
</body>
</html>