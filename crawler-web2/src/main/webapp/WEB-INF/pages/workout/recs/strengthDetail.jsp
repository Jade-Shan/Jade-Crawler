<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>记录力量运动</title>
	<script type="text/javascript" src="http://7xldv2.com1.z0.glb.clouddn.com/3rd/zepto-1.1.2.min.js"></script>
	<script type="text/javascript" src="${cdnjadeutils}scripts/jadeutils.min.js"></script>
	<script type="text/javascript" src="${cdnworkout}scripts/workout.min.js"></script>
	<link rel="stylesheet" href="${cdnworkout}styles/workout.min.css" />
</head>
<body>
	<div id="logindiv">
		<input type="text" id="username" name="username" class="ipt-normal" value="">
		<input type="password" id="password" name="password" class="ipt-normal" value="">
		<input type="button" id="login" value="login" class="sbmt-normal">
	</div>
	<div id="userinfodiv">
		<em class="lb-ipt">Welcome !</em>
		<em id="lb-username" class="lb-ipt">username</em>
		<input type="button" id="logout" value="Login Out" class="sbmt-normal">
	</div>
	<ul id="workoutinfo">
		<li>
			<em class="lb-ipt">Name：</em>
			<em class="lb-ipt" id="lb-itemname">item name</em>
			<input type="hidden" id="item" name="item" class="ipt-normal" value="sth-2-1">
		</li>
		<li>
			<em class="lb-ipt">Weight：</em>
			<input type="text" id="weight" name="weight" class="ipt-normal" value="60">
		</li>
		<li>
			<em class="lb-ipt">Repeat：</em>
			<input type="text" id="repeat" name="repeat" class="ipt-normal" value="15">
		</li>
		<li>
			<input type="button" id="record" value="record" class="sbmt-normal">
		</li>
	</ul>
</body>
<script>
$(document).ready(function() {
		workoutApp.userAuth.barinit();
		$('#weight').val(jadeUtils.cookieOperator('weight'));
		$('#repeat').val(jadeUtils.cookieOperator('repeat'));

		$('#record').on('click', function(event) {
			workoutApp.workoutRec.recordStrengthRec();
			});
		});
</script>
</html>

