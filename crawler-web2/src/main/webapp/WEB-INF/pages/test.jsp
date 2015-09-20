<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>test page</title>
</head>
<body>
<form action="<c:url value="/user.html" />">
	<input type="text" name="userName" value="${cdnjadeutils}"/>
	<input type="text" name="userName" value="${cdnworkout}"/>
</form>
<table>
	<tr><th>Value</th><th>Square</th></tr>
	<c:forEach var="x" begin="0" end="10" step="2">
	<tr><td><c:out value="${x}"/></td><td><c:out value="${x * x}"/></td></tr>
	</c:forEach>
</table>
</body>
</html>
