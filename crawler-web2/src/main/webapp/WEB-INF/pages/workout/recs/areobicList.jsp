<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>有氧训练</title>
	<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript" src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${cdnjadeutils}scripts/jadeutils.js"></script>
	<script type="text/javascript" src="${cdnworkout}scripts/workout.js"></script>
	<link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="${cdnworkout}styles/workout.min.css"/>
</head>
<body>
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


