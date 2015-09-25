<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>有氧训练</title>
	<script type="text/javascript" src="${cdn3rd}/zepto-1.1.2.min.js"></script>
	<script type="text/javascript" src="${cdnjadeutils}scripts/jadeutils.js"></script>
	<script type="text/javascript" src="${cdnworkout}scripts/workout.js"></script>
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
	</ul>
</body>
<script>
workoutApp.workoutRec.showAeroboicItems = function () {
	var html = '';
	$.each(workoutApp.workout.AerobicItems, function(index, item) {
		html = html + '<li><img class="img-w-lst" src="' + $("#cdnworkout").val() + 
			'images/workout/' + item.id + '.svg" /><em class="lst-ipt">' + 
		 	item.name + '</em><em class="lst-ipt">(' + item.ename + ')</em><em>' + 
		 	'<input type="button" item="' + item.id + '" value="record" class="sbmt-normal go-detail" /></em></li>'
	});
	$("#workoutinfo").html(html);

	$(".go-detail").each(function (idx, item) {
			$(item).on("click", function (e) {
				jadeUtils.url.goUrl(workoutApp.appPath + 
					"/page/workout/aerobicWorkout/details/" + $(this).attr("item"));
				});
			});
};

$(document).ready(function() {
		workoutApp.userAuth.barinit();
		workoutApp.workoutRec.showAeroboicItems();
});
</script>
</html>


