<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="kr.or.iei.notice.model.vo.*"
	import ="kr.or.iei.member.model.vo.*"
	import ="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글내용</title>
</head>
<body>
	<%@ include file = "/views/layout/header.jsp" %>

	<%
		NoticeData nd = (NoticeData)request.getAttribute("noticeData");
		ArrayList<Comment> list = nd.getList();
		Notice notice = nd.getNotice();
	%>

	<center>
		<%-- <h1>글내용</h1>
		<table border="1">
			<tr>
				<td>글번호</td>
				<td>글제목</td>
				<td>글쓴이</td>
				<td>작성일</td>
			</tr>

			<tr>
				<td><%=notice.getNoticeNo()%></td>
				<td><%=notice.getSubject()%></td>
				<td><%=notice.getUserId()%></td>
				<td><%=notice.getRegDate()%></td>
			</tr>

			<tr>
				<td colspan="4"><%=notice.getContents() %></td>
			</tr>
		</table> --%>
	-----------------------------------------<Br>
	 제목 길이 : <%=notice.getSubject().length() %><Br>
	 글길이  : <%=notice.getContents().length() %><br>
	-----------------------------------------<br>

		글번호 : <%=notice.getNoticeNo()%><br>
		글제목 : <span id = "subject"><%=notice.getSubject()%></span>
			  <input type = "hidden" id = "subject_fd" value = "<%=notice.getSubject()%>"/><br>
 		글쓴이 : <%=notice.getUserId()%><br>
		작성일 : <%=notice.getRegDate()%><br>
		글내용 : <span id = "contents"><%=notice.getContents()%></span>
			  <textarea id = "contents_fd" style = "display:none;" rows = "5" cols = "50"><%=notice.getContents()%></textarea><br>
		
		
		<form action = "/noticeUpdate.do" method = "post" id = "noticeUpdateForm">
			<input type = "hidden" name = "noticeNo" value = "<%=notice.getNoticeNo()%>"/>
			<input type = "hidden" name = "subject" id = "subject_form"/>
			<input type = "hidden" name = "contents" id = "contents_form"/>
		</form>
	
	<%	//작성자가 아니면 수정버튼을 보여주지 않는 코드
		Member m = (Member)request.getSession(false).getAttribute("member");
	
		if(m!=null){
			String user1 = m.getUserId();
			String user2 = notice.getUserId();
		
		
		if(user1.equals(user2)){
	%>
	<button id = "btn1" onclick = "modifyActive();">수정</button>
	<button id = "btn2" onclick = "deleteNotice();">삭제</button>
	<%	} 
	}%>
	
	
	<form>
		<input type = "hidden" name = "noticeNo" value = "<%=notice.getNoticeNo() %>"/>
	</form>
	
	<button onclick = "listBtn();">목록</button>
	</center>
	
	<script>
		function listBtn(){
			location.href ="/noticeList.do";
		}
		
		function modifyActive() {
			
			 document.getElementById("subject").style.display="none";
		      document.getElementById("subject_fd").type="text";
		      
		      document.getElementById("contents").style.display="none";
		      document.getElementById("contents_fd").style.display="block";
		      
		      document.getElementById("btn2").innerHTML="취소";
		      document.getElementById("btn2").onclick=function(){cancelNotice()};
		      
		      document.getElementById("btn1").innerHTML="수정제출";
		      document.getElementById("btn1").onclick=function(){modifySubmit()};
		}
		
		function modifySubmit() {
			
			 document.getElementById("subject_form").value = document.getElementById("subject_fd").value;
		      document.getElementById("contents_form").value = document.getElementById("contents_fd").value;
		      document.getElementById("noticeUpdateForm").submit();
		}
		
		function deleteNotice() {
			location.href= "/noticeDelete.do?noticeNo=<%=notice.getNoticeNo()%>&writer=<%=notice.getUserId()%>";
		}
		
		function cancelNotice() {
		      document.getElementById("subject").style.display="inline";
		      document.getElementById("subject_fd").type="hidden";
		      document.getElementById("subject_fd").value=document.getElementById("subject").innerHTML;  //수정하고 취소눌렀을때 다시 원본의 상태로 돌아가게
		      
		      //글내용
		      document.getElementById("contents").style.display="inline";
		      document.getElementById("contents_fd").style.display="none";
		      document.getElementById("contents_fd").value=document.getElementById("contents").innerHTML;
		      
		      document.getElementById("btn2").innerHTML="삭제";
		      document.getElementById("btn2").onclick=function(){deleteNotice()}	;
		      		
		      document.getElementById("btn1").innerHTML="수정";
		      document.getElementById("btn1").onclick=function(){modifyActive()};

		}
		
	</script>
	
	<center>
	<br>
	<h3>댓글</h3>
	
	<form action = "/noticeInsertComment.do" method = "post">
		<%if(session.getAttribute("member") != null) {%>
		<textarea name = "comment" rows = "3" cols ="50" style = "resize:none;"></textarea>
		<input type = "hidden" name = "noticeNo" value = "<%=notice.getNoticeNo()%>"/>
		<input type = "submit" value = "댓글달기"/>
		<%} else {%>
		<textarea onclick = "login();" name = "comment" rows = "3" cols ="50" style = "resize:none;"></textarea>
		<%}%>
	</form>
	
	<script>
		function login() {
			window.open("/views/member/loginPopup.jsp", "_blank", "width=400px, height =180px");
			return false;
		}
	</script>
	<br>
	<%if(!list.isEmpty()){%>
		<table>
			<tr>
				<th>댓글내용</th><th>작성자</th><th>작성일</th>
			</tr>
			<%for(Comment c : list){%>
				<tr>
					<td width=300><%=c.getContent() %></td>
					<td width=100><%=c.getUserId() %></td>
					<td width=100><%=c.getRegdate() %></td>
				</tr>
			<%}%>
		</table>
	<%} else {%>
		<h4>댓글이 없습니다.</h4>
	<%} %>
	</center>
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</body>
</html>