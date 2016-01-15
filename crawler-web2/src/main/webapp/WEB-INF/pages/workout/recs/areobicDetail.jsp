<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<title>记录有氧运动</title>
	<%@ include file="/WEB-INF/pages/workout/common/commhead.jsp" %>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker.min.css">
</head>
<body>
	<%@ include file="/WEB-INF/pages/workout/common/navbar.jsp" %>
	<div class="container">
		<ul id="workoutinfo" class="list-unstyled">
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
				<em class="lb-ipt">Date：</em>
				<input id="dateIpt" data-provide="datepicker" data-date-format="yyyy/mm/dd" >
			</li>
			<li>
				<input type="button" id="record" value="record" class="sbmt-normal">
			</li>
			<li>
				<table class="table table-striped">
					<caption>历史记录</caption>
					<thead>
						<tr id="recTitles"></tr>
					</thead>
					<tbody id="recs"></tbody>
				</table>
			</li>
		</ul>
	</div>
</body>
<script>

$(document).ready(function() {
		workoutApp.userAuth.barinit();

		$('#record').on('click', function(event) {
			var username = $('#username').val();
			var password = $('#password').val();
			var workoutId= $('#workoutId').val();
			var time     = $('#time').val();
			var distance = $('#distance').val();
			var calories = $('#calories').val();
			var logTime  = parseDate($('#dateIpt').val()).getTime();
			workoutApp.workoutRec.recordAerobicRec(username, password, workoutId, 
				time, distance, calories, logTime, function (data, status, xhr) {
					console.debug(data);
					jadeUtils.cookieOperator('time'     + workoutId, time    );
					jadeUtils.cookieOperator('distance' + workoutId, distance);
					jadeUtils.cookieOperator('calories' + workoutId, calories);
					workoutApp.workoutRec.findAerobicRec(username, password, workoutId, 
						0, (new Date()).getTime(),
						// (new Date()).getTime() - 604800000, (new Date()).getTime(),
						workoutApp.workoutRec.renderAerobicRecDetailPage);
			});
		});

		var username  = $('#username').val();
		var password  = $('#password').val();
		var workoutId = $('#workoutId').val();

		var rec = workoutApp.workout.AerobicItemMap.get(workoutId);
		$("#itm-name").html(rec.name);
		$("#itm-ename").html("(" + rec.ename + ")");
		$("#w-img").html("<img class='img-w-exp' src='" + $("#cdnworkout").val() +
			"images/workout/" + rec.id + ".svg' />")

		$('#time'    ).val(jadeUtils.cookieOperator('time'     + workoutId));
		$('#distance').val(jadeUtils.cookieOperator('distance' + workoutId));
		$('#calories').val(jadeUtils.cookieOperator('calories' + workoutId));

		var timeArea = jadeUtils.time.getTimeArea(new Date(), -1);
		console.debug(timeArea.floor);
		console.debug(timeArea.ceil);
		console.debug(timeArea.floor.getTime());
		console.debug(timeArea.ceil.getTime());

		workoutApp.workoutRec.findAerobicRec(username, password, workoutId, 
				0, (new Date()).getTime(),
				// (new Date()).getTime() - 604800000, (new Date()).getTime(),
				workoutApp.workoutRec.renderAerobicRecDetailPage);
});
</script>
</html>

