<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<title>有氧训练</title>
	<%@ include file="/WEB-INF/pages/workout/common/commhead.jsp" %>
</head>
<body>
	<%@ include file="/WEB-INF/pages/workout/common/navbar.jsp" %>
	<div class="container">
		<ul id="workoutinfo"></ul>
	</div>
</body>
<script>
$(document).ready(function() {
		workoutApp.userAuth.barinit();
		workoutApp.workoutRec.showAeroboicItems();
});
</script>
</html>


