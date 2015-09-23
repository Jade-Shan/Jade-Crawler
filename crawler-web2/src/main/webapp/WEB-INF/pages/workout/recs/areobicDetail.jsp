<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>记录有氧运动</title>
	<script type="text/javascript" src="${cdn3rd}/zepto-1.1.2.min.js"></script>
	<script type="text/javascript" src="${cdn3rd}/d3.min.js"></script>
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
			<em class="lb-ipt">Time：</em>
			<input type="text" id="time" name="time" class="ipt-normal" value="">
			<em class="lb-ipt">min</em>
		</li>
		<li>
			<em class="lb-ipt">Distance：</em>
			<input type="text" id="distance" name="distance" class="ipt-normal" value="15">
		</li>
		<li>
			<em class="lb-ipt">Calories：</em>
			<input type="text" id="calories" name="calories" class="ipt-normal" value="15">
		</li>
		<li>
			<input type="button" id="record" value="record" class="sbmt-normal">
		</li>
		<li>
		<ul id="historyRec" class="lb-ipt"></ul>
		</li>
	</ul>
</body>
<script>

$(document).ready(function() {
		workoutApp.userAuth.barinit();
		var workoutId = $("#workoutId").val();

		var rec = workoutApp.workout.AerobicItemMap.get(workoutId);
		$("#itm-name").html(rec.name);
		$("#itm-ename").html("(" + rec.ename + ")");
		$("#w-img").html("<img class='img-w-exp' src='" + $("#cdnworkout").val() +
			"images/workout/" + rec.id + ".svg' />")

		$('#record').on('click', function(event) {
			workoutApp.workoutRec.recordAerobicRec();
			});

		$('#time'    ).val(jadeUtils.cookieOperator('time'     + workoutId));
		$('#distance').val(jadeUtils.cookieOperator('distance' + workoutId));
		$('#calories').val(jadeUtils.cookieOperator('calories' + workoutId));

		var timeArea = jadeUtils.time.getTimeArea(new Date(), -1);
		console.debug(timeArea.floor);
		console.debug(timeArea.ceil);
		console.debug(timeArea.floor.getTime());
		console.debug(timeArea.ceil.getTime());

		workoutApp.workoutRec.findAerobicRec($('#username').val(), $('#password').val(), 
				$('#workoutId').val(), 
				0,
				// timeArea.floor.getTime(),
				(new Date()).getTime(),
				// timeArea.ceil.getTime(), 
				function (data) {
				var html = "";
				$.each(data.result, function (idx, item) {
						var t = new Date();
						t.setTime(item.logTime);
	
						html = html + '<li>' + jadeUtils.time.getLocalTimeStr(t) + 
							'<ul><li>Time: ' + item.time + 
							'</li><li>Distance: ' + item.distance + 
							'</li><li>Caliories: ' + item.calories + '</li></ul></li>';
					});
					$('#historyRec').html(html);
				});
		});
</script>
</html>

