<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지사항 작성</title>
</head>
<body>
<center>
	<h3>공지사항 작성</h3>
	<form action = "/noticeWrite.do" method = "post">
		<input type = "text" size = 99 placeholder="제목입력해주세요" name = "subject"/><br><br>
		<textarea rows = "20" cols = "100" placeholder="내용입력해주세요" name = "contents" style = "resize:none;"></textarea><br><br>
		<input type = "submit" value = "작성하기"/>
		<input type = "reset" value = "취소"/>
		<button onclick = "return back()">목록</button>
	</form>
</center>
	<script>
		function back() {
			history.go(-1);
			return false;
		}
	</script>
</body>
</html>