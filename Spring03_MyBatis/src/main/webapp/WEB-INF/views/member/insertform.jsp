<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insertform.jsp</title>
</head>
<body>
<form action="insert.do" method="post">
	<label for="name">이름</label>
	<input type="text" name="name" id="name" />
	<label for="addr">주소</label>
	<input type="text" name="addr" id="addr" />
	<button type="submit">추가</button>
</form>
</body>
</html>