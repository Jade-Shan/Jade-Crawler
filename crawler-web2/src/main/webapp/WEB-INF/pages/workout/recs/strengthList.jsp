<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<title>记录训练</title>
	<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.0.0/jquery.min.js"></script>
	<script type="text/javascript" src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="${cdnjadeutils}scripts/jadeutils.js"></script>
	<script type="text/javascript" src="${cdnworkout}scripts/workout.js"></script>
	<link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">
	<link rel="stylesheet" href="${cdnworkout}styles/workout.min.css"/>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/workout/common/navbar.jsp"/>
	<div class="container">
		<div class="workoutrecs">
			<dl>
				<dt>肩膀</dt>
				<dd>
				<dl>
					<dt id="tit-shoulder1">器械</dt><dd><ul id="shoulder1" class="dtlitms"></ul></dd>
					<dt id="tit-shoulder2">重量</dt><dd><ul id="shoulder2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
			<dl>
				<dt>胸部</dt>
				<dd>
				<dl>
					<dt id="tit-chest1">器械</dt><dd><ul id="chest1" class="dtlitms"></ul></dd>
					<dt id="tit-chest2">重量</dt><dd><ul id="chest2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
			<dl>
				<dt>背部</dt>
				<dd>
				<dl>
					<dt id="tit-back1">器械</dt><dd><ul id="back1" class="dtlitms"></ul></dd>
					<dt id="tit-back2">重量</dt><dd><ul id="back2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
			<dl>
				<dt>腰部</dt>
				<dd>
				<dl>
					<dt id="tit-waist1">器械</dt><dd><ul id="waist1" class="dtlitms"></ul></dd>
					<dt id="tit-waist2">重量</dt><dd><ul id="waist2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
			<dl>
				<dt>大臂</dt>
				<dd>
				<dl>
					<dt id="tit-upperarm1">器械</dt><dd><ul id="upperarm1" class="dtlitms"></ul></dd>
					<dt id="tit-upperarm2">重量</dt><dd><ul id="upperarm2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
			<dl>
				<dt>小臂</dt>
				<dd>
				<dl>
					<dt id="tit-formearm1">器械</dt><dd><ul id="formearm1" class="dtlitms"></ul></dd>
					<dt id="tit-formearm2">重量</dt><dd><ul id="formearm2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
			<dl>
				<dt>大腿</dt>
				<dd>
				<dl>
					<dt id="tit-thigh1">器械</dt><dd><ul id="thigh1" class="dtlitms"></ul></dd>
					<dt id="tit-thigh2">重量</dt><dd><ul id="thigh2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
			<dl>
				<dt>小腿</dt>
				<dd>
				<dl>
					<dt id="tit-calve1">器械</dt><dd><ul id="calve1" class="dtlitms"></ul></dd>
					<dt id="tit-calve2">重量</dt><dd><ul id="calve2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
			<dl>
				<dt>腹部</dt>
				<dd>
				<dl>
					<dt id="tit-abs1">器械</dt><dd><ul id="abs1" class="dtlitms"></ul></dd>
					<dt id="tit-abs2">重量</dt><dd><ul id="abs2" class="dtlitms"></ul></dd>
				</dl>
				</dd>
			</dl>
		</div>
	</div>
</body>
<script>
$(document).ready(function() {
		workoutApp.userAuth.barinit();
		workoutApp.workoutRec.showStrengthItems();
});
</script>
</html>

