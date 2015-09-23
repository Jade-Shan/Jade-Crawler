var cdnworkout = "http://7xldv2.com1.z0.glb.clouddn.com/workout/";

var workoutApp = {};
workoutApp.appPath = "/crawler-web2";

workoutApp.userAuth = { };

workoutApp.userAuth.checkLogin = function (
		username, password, successCallback, failCallback, errorCallback) 
{
	if ('' !== username && '' !== password) {
		var auth = 'Basic ' + jadeUtils.string.base64encode(
				jadeUtils.string.utf16to8(username + ':' + password)); 
		$.ajax({ type: 'POST', dataType: 'json', timeout: 3000,
				url: workoutApp.appPath + '/api/workout/user/auth', 
				headers: {Authorization: auth},
				data: { },
				success: function(data, status, xhr) {
					console.debug(status);
					if ('success' == data.status && 'success' == data.auth) {
						successCallback(data);
					} else {
						failCallback(data);
					}
				},
				error: function(xhr, errorType, error) { errorCallback(error); },
				complete: function(xhr, status) {}
			});
	} else {
		console.debug("no username or password");
	}
};

workoutApp.userAuth.barinit = function () {
	var login = function (username, password) {
		workoutApp.userAuth.checkLogin(username, password, function(data) {
			jadeUtils.cookieOperator('username', username);
			jadeUtils.cookieOperator('password', password);
			$('#logindiv').hide();
			$('#lb-username').html(username);
			$('#userinfodiv').show();
		}, function (data) {
			console.debug(data.reason);
			alert(data.reason);
		}, function (data) {
			alert("Ajax Error");
		});
	};
	$('#login').on('click', function(event) {
		var username = $('#username').val();
		var password = $('#password').val();
		login(username, password);
	});

	$('#logout').on('click', function(event) {
		$('#userinfodiv').hide();
		$('#logindiv').show();
	});

	var username = jadeUtils.cookieOperator('username');
	var password = jadeUtils.cookieOperator('password');
	$('#username').val(username);
	$('#password').val(password);
	login(username, password);
};

