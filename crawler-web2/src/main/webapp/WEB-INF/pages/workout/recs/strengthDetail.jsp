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
		<li>
			<div id="muscle-front-data"></div>
			<div id="muscle-back-data" ></div>
		</li>
	</ul>
</body>
<script>

workoutApp.muscle.MuscleDataFpath = $("#cdnworkout").val() + "datas/muscle-front.data";
workoutApp.muscle.MuscleDataBpath = $("#cdnworkout").val() + "datas/muscle-back.data";

/**
 * 加载肌肉图片（Svg格式）
 */
workoutApp.muscle.loadMuscleImg = function (cId, width, height, scale, url, callback) {
	$.get(url, function (data, status, xhr) {
		if (200 == xhr.status && "success" == status) {
			var html = '<svg xmlns="http://www.w3.org/2000/svg"' +
		'width="' + width + '" height="' + height + '">' + 
		'<g transform="scale(' + scale + ')">' + data + '</g></svg>';
	$("#"+cId).html(html);
	workoutApp.muscle.initMuscleImage(cId);
	callback();
		}
	});
};

workoutApp.muscle.markMusclesPrimary = function (ids) {
	if(ids.length > 0)
	$.each(ids, function(index, item){
			$("." + item).attr("class","muscle-primary");
			});
};
workoutApp.muscle.markMusclesMinor = function (ids) {
	if(ids.length > 0)
	$.each(ids, function(index, item){
			$("." + item).attr("class","muscle-minor");
			});
};
workoutApp.muscle.markMusclesExtra = function (ids) {
	if(ids.length > 0)
	$.each(ids, function(index, item){
			$("." + item).attr("class","muscle-extra");
			});
};

workoutApp.muscle.loadMarkedMuscles = function (fid, bid, width, height, scale, marks) {
	workoutApp.muscle.loadMuscleImg(fid, width, height, scale,
			workoutApp.muscle.MuscleDataFpath, function() {
				workoutApp.muscle.loadMuscleImg(bid, width, height, scale, 
					workoutApp.muscle.MuscleDataBpath, function () {
						workoutApp.muscle.markMusclesExtra(marks.ext);
						workoutApp.muscle.markMusclesPrimary(marks.pim);
						workoutApp.muscle.markMusclesMinor(marks.min);
					});
			});
};



$(document).ready(function() {
		workoutApp.userAuth.barinit();

		$('#record').on('click', function(event) {
			workoutApp.workoutRec.recordStrengthRec();
			});

		var rec = workoutApp.workout.StrengthItemMap.get($("#item").val());
		$("#itm-name").html(rec.name);
		$("#itm-ename").html("(" + rec.ename + ")");
		$("#w-img").html("<img class='img-w-exp' src='" + $("#cdnworkout").val() +
			"images/workout/" + rec.id + ".svg' />")

		workoutApp.muscle.loadMarkedMuscles(
			"muscle-front-data", "muscle-back-data", 270, 500, 0.5, rec);

		$('#weight').val(jadeUtils.cookieOperator('weight'));
		$('#repeat').val(jadeUtils.cookieOperator('repeat'));
});
</script>
</html>

