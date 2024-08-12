<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="minsert" mehtod="post">
		ID : <input name="id"><br><br>
		NAME : <input name="name"><br><br>
		PASSWORD : <input type="password" name="password"><br><br>
		<button>회원가입</button>
	</form>
	<br>
	<hr>
	<br>
	<form action="binsert" method="post">
		제목 : <input name="title"><br><br>
		내용 : <input name="content"><br><br>
		<button>게시글 작성</button>
	</form>
	<br>
	<hr>
	<br>
	<!-- <a href="mupdate?id=user01&name=김수정">멤버 이름 수정</a> -->
	
	<!-- 유저 id를 받아서 이름 수정하기-->
	<c:if test="${not empty error}">
		아이디가 존재하지 않습니다~ 다시 쓰세용<br><br>
	</c:if>
	<form action="modify" method="post">
		아이디 : <input name="id"><br><br>
		수정할 이름 : <input name="name"><br><br>
		<button>이름 수정</button>
	</form>
</body>
</html>