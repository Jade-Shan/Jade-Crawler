<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<%@ page isELIgnored="false" %>  

	<input type="hidden" id="cdnworkout" name="cdnworkout" value="${cdnworkout}">

	<nav class="navbar navbar-default" role="navigation">
		<div class="navbar-header">
			<a class="navbar-brand" href="/crawler-web2/page/workout/index.html">Jade Workout</a>
		</div>
		<div>
			<ul id="mu-workoutrec" class="nav navbar-nav navbar-left">
				<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					Record Workout<b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li><a href="/crawler-web2/page/workout/strengthWorkout/list">Strength Workout</a></li>
					<li class="divider"></li>
					<li><a href="/crawler-web2/page/workout/aerobicWorkout/list">Aerobic Workout</a></li>
				</ul>
				</li>
			</ul>
			<!-- 用户相关信息 -->
			<ul id="div-userinfo" class="nav navbar-nav navbar-right">
				<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">
					Signed in as <b id="lb-username">Thomas</b> <b class="caret"></b>
				</a>
				<ul class="dropdown-menu">
					<li><a href="#">testing</a></li>
					<li><a href="#">testing</a></li>
					<li><a href="#">testing</a></li>
					<li class="divider"></li>
					<li><a href="#">testing</a></li>
					<li class="divider"></li>
					<li><button type="button" id="logout" class="btn btn-default center-block">Logout</button></li>
				</ul>
				</li>
			</ul>
			<form id="div-userlogin" class="navbar-form navbar-right form-inline" role="search">
				<input type="text" id="username" name="username" class="form-control ipt-username" placeholder="username" value="">
				<input type="password" id="password" name="password" class="form-control ipt-password" placeholder="password" value="">
				<button type="button" id="login" class="btn btn-default">Login</button>
			</form> 
		</div>
	</nav>
