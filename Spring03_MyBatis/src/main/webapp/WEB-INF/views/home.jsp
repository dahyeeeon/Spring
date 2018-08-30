<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>home.jsp</title>
</head>
<body>
<ul>
	<li><a href="member/list.do">회원목록보기</a></li>
	<li><a href="users/signup_form.do">회원가입</a></li>
	<li><a href="users/loginform.do">로그인</a></li>
	<li><a href="file/list.do">파일 리스트</a></li>
	<li><a href="cafe/list.do">카페글 리스트</a></li>
	<li><a href="shop/list.do">상품 목록 리스트</a></li>
	<!-- session 영역에 id가 비어있지 않으면(로그인된 상태면) -->
	<c:if test="${not empty id }">
	<p><strong><a href="users/info.do">${id }</a></strong></p>
	<li><a href="users/logout.do">로그아웃</a></li>
	</c:if>
	

</ul>
</body>
</html>