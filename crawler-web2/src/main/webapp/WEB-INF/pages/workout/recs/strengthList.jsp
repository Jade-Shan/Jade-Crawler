<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  
<!DOCTYPE html>
<html lang="en">
<head>
	<title>记录训练</title>
	<%@ include file="/WEB-INF/pages/workout/common/commhead.jsp" %>
</head>
<body>

	<%@ include file="/WEB-INF/pages/workout/common/navbar.jsp" %>

	<div class="container">
		<div class="panel-group" id="accordion">

			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse1">肩膀 - 器械</a>
					</h4>
				</div>
				<div id="collapse1" class="panel-collapse collapse in">
					<div id="shoulder1"  class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse2">肩膀 - 重量</a>
					</h4>
				</div>
				<div id="collapse2" class="panel-collapse collapse">
					<div id="shoulder2" class="panel-body"></div>
				</div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse3">胸部 - 器械</a>
					</h4>
				</div>
				<div id="collapse3" class="panel-collapse collapse">
					<div id="chest1" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse4">胸部 - 重量</a>
					</h4>
				</div>
				<div id="collapse4" class="panel-collapse collapse">
					<div id="chest2" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse5">背部 - 器械</a>
					</h4>
				</div>
				<div id="collapse5" class="panel-collapse collapse">
					<div id="back1" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse6">背部 - 重量</a>
					</h4>
				</div>
				<div id="collapse6" class="panel-collapse collapse">
					<div id="back2" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse7">腰部 - 器械</a>
					</h4>
				</div>
				<div id="collapse7" class="panel-collapse collapse">
					<div id="waist1" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse8">腰部 - 重量</a>
					</h4>
				</div>
				<div id="collapse8" class="panel-collapse collapse">
					<div id="waist2" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse9">大臂 - 器械</a>
					</h4>
				</div>
				<div id="collapse9" class="panel-collapse collapse">
					<div id="upperarm1" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse10">大臂 - 重量</a>
					</h4>
				</div>
				<div id="collapse10" class="panel-collapse collapse">
					<div id="upperarm2" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse11">小臂 - 器械</a>
					</h4>
				</div>
				<div id="collapse11" class="panel-collapse collapse">
					<div id="formearm1" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse12">小臂 - 重量</a>
					</h4>
				</div>
				<div id="collapse12" class="panel-collapse collapse">
					<div id="formearm2" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse13">大腿 - 器械</a>
					</h4>
				</div>
				<div id="collapse13" class="panel-collapse collapse">
					<div id="thigh1" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse14">大腿 - 重量</a>
					</h4>
				</div>
				<div id="collapse14" class="panel-collapse collapse">
					<div id="thigh2" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse15">小腿 - 器械</a>
					</h4>
				</div>
				<div id="collapse15" class="panel-collapse collapse">
					<div id="calve1" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse16">小腿 - 重量</a>
					</h4>
				</div>
				<div id="collapse16" class="panel-collapse collapse">
					<div id="calve2" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse17">腹部 - 器械</a>
					</h4>
				</div>
				<div id="collapse17" class="panel-collapse collapse">
					<div id="abs1" class="panel-body"></div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" 
							href="#collapse18">腹部 - 重量</a>
					</h4>
				</div>
				<div id="collapse18" class="panel-collapse collapse">
					<div id="abs2" class="panel-body"></div>
				</div>
			</div>

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

