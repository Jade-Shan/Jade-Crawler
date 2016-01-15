<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<title>记录力量运动</title>
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
				<em class="lb-ipt">Weight：</em>
				<input type="text" id="weight" name="weight" class="ipt-normal" value="60">
			</li>
			<li>
				<em class="lb-ipt">Repeat：</em>
				<input type="text" id="repeat" name="repeat" class="ipt-normal" value="15">
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
	</div>
</body>
<script>
$(document).ready(function() {
		workoutApp.userAuth.barinit();

		$('#record').on('click', function(event) {
			var username = $('#username').val();
			var password = $('#password').val();
			var workoutId= $('#workoutId').val();
			var weight   = $('#weight').val();
			var repeat   = $('#repeat').val();
			var logTime  = parseDate($('#dateIpt').val()).getTime();
			workoutApp.workoutRec.recordStrengthRec(username, password, workoutId, 
				weight, repeat, logTime, function (data, status, xhr) {
					console.debug(data);
					jadeUtils.cookieOperator('weight' + workoutId, weight);
					jadeUtils.cookieOperator('repeat' + workoutId, repeat);
					workoutApp.workoutRec.findStrengthRec(username, password, workoutId, 
						0, (new Date()).getTime(),
						// (new Date()).getTime() - 604800000, (new Date()).getTime(),
						workoutApp.workoutRec.renderStrengthRecDetailPage);
			});
		});

		var username  = $('#username').val();
		var password  = $('#password').val();
		var workoutId = $("#workoutId").val();

		var rec = workoutApp.workout.StrengthItemMap.get(workoutId);
		$("#itm-name").html(rec.name);
		$("#itm-ename").html("(" + rec.ename + ")");
		$("#w-img").html("<img class='img-w-exp' src='" + $("#cdnworkout").val() +
			"images/workout/" + rec.id + ".svg' />")

		workoutApp.muscle.loadMarkedMuscles(
			"muscle-front-data", "muscle-back-data", 270, 500, 0.5, rec);

		$('#weight').val(jadeUtils.cookieOperator('weight' + workoutId));
		$('#repeat').val(jadeUtils.cookieOperator('repeat' + workoutId));

		var timeArea = jadeUtils.time.getTimeArea(new Date(), -1);
		console.debug(timeArea.floor);
		console.debug(timeArea.ceil);
		console.debug(timeArea.floor.getTime());
		console.debug(timeArea.ceil.getTime());

		workoutApp.workoutRec.findStrengthRec(username, password, workoutId, 
				0, (new Date()).getTime(),
				// (new Date()).getTime() - 604800000, (new Date()).getTime(),
				workoutApp.workoutRec.renderStrengthRecDetailPage);

		$('.datepicker').datepicker();
});
</script>
</html>

