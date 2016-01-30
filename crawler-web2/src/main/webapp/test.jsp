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
<form action="<c:url value="/api/journal/recordJournal" />" method="post">
	<input type="text" name="auth" value="Jade Shan"/><br/>
	<input type="text" name="title" value=""/><br/>
	<textarea name="text" rows="10" cols="30"></textarea><br/>
<input type="submit" value="Submit">
</form>
</body>
</html>
