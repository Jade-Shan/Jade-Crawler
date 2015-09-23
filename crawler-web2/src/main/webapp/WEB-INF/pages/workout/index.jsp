<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>训练记录</title>
	<script type="text/javascript" src="${cdn3rd}/zepto-1.1.2.min.js"></script>
	<script type="text/javascript" src="${cdnjadeutils}scripts/jadeutils.min.js"></script>
	<script type="text/javascript" src="${cdnworkout}scripts/workout.min.js"></script>
	<link rel="stylesheet" href="${cdnworkout}styles/workout.min.css" />
</head>
<body>
	<div id="logindiv">
		<input type="text" id="username" name="username" class="ipt-normal" value="">
		<input type="password" id="password" name="password" class="ipt-normal" value="">
		<input type="button" id="login" value="login" class="sbmt-normal">
		<input type="hidden" id="cdnworkout" name="cdnworkout" value="${cdnworkout}">
	</div>
	<div id="userinfodiv">
		<em class="lb-ipt">Welcome !</em>
		<em id="lb-username" class="lb-ipt">username</em>
		<input type="button" id="logout" value="Login Out" class="sbmt-normal">
	</div>
	<div>
		<input type="button" id="strength" value="Strength Workout" class="sbmt-normal">
		<input type="button" id="aerobic" value="Aerobic Workout" class="sbmt-normal">
	</div>
</body>
<script>
$(document).ready(function() {
		workoutApp.userAuth.barinit();
		$("#strength").on("click", function (e) {
			jadeUtils.url.goUrl(workoutApp.appPath + 
				"/page/workout/strengthWorkout/list");
			});
		$("#aerobic").on("click", function (e) {
			jadeUtils.url.goUrl(workoutApp.appPath + 
				"/page/workout/aerobicWorkout/list");
			});
});
</script>
</html>
