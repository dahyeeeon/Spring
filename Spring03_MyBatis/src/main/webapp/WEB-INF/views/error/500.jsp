<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>.jsp</title>
</head>
<p>내부오류.곧 고치겠습니다....</p>
<p>${exception.message }</p>
<a href="${pageContext.request.contextPath }/">인덱스로 가기</a>
</body>
</html>