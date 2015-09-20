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
		<input type="hidden" id="cdnworkout" name="cdnworkout" value="${cdnworkout}">
	</div>
	<div id="userinfodiv">
		<em class="lb-ipt">Welcome !</em>
		<em id="lb-username" class="lb-ipt">username</em>
		<input type="button" id="logout" value="Login Out" class="sbmt-normal">
	</div>
	<ul id="workoutinfo">
		<li id="w-img"></li>
		<li>
			<em class="lb-ipt">Name：</em>
			<em class="lb-ipt" id="itm-name"></em>
			<em class="lb-ipt" id="itm-ename"></em>
			<input type="hidden" id="workoutId" name="workoutId" class="ipt-normal" value="${workoutId}">
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
		<li>
		<table>
			<tr>
				<td>
			<div id="muscle-front-data" style="width: 210; display: block"></div>
				</td>
				<td>
			<div id="muscle-back-data"  style="width: 210; display: block"></div>
				</td>
			</tr>
		</table>

		</li>
	</ul>
</body>
<script>

$(document).ready(function() {
		workoutApp.userAuth.barinit();

		var rec = workoutApp.workout.StrengthItemMap.get($("#workoutId").val());
		$("#itm-name").html(rec.name);
		$("#itm-ename").html("(" + rec.ename + ")");
		$("#w-img").html("<img class='img-w-exp' src='" + $("#cdnworkout").val() +
			"images/workout/" + rec.id + ".svg' />")

		workoutApp.muscle.loadMarkedMuscles(
			"muscle-front-data", "muscle-back-data", 270, 500, 0.5, rec);

		$('#record').on('click', function(event) {
			workoutApp.workoutRec.recordStrengthRec();
			});

		$('#weight').val(jadeUtils.cookieOperator('weight'));
		$('#repeat').val(jadeUtils.cookieOperator('repeat'));
});
</script>
</html>

