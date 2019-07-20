<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>训练记录</title>
	<%@ include file="/WEB-INF/pages/workout/common/commhead.jsp" %>
</head>
<body>
	<%@ include file="/WEB-INF/pages/workout/common/navbar.jsp" %>
	<div class="container">
	</div>
</body>
<script>
$(document).ready(function() { workoutApp.userAuth.barinit(); });
</script>
</html>
