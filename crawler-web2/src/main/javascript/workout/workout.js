workoutApp.workout = {};

workoutApp.workout.AerobicItems = [
{id: "aero-1-1", name: "慢跑", ename: "Jogging"},
{id: "aero-2-1", name: "游泳", ename: "Swimming"},
{id: "aero-3-1", name: "椭圆机", ename: "Elliptical Machine"},
];

workoutApp.workout.StrengthItems = [
	{part: "肩部", epart: "Shoulder", type: "fixed", id: "sth-1-1", name: "器械推举", ename: "Shoulder Press", 
		pim: ["4-1", "4-2", "4-3"], min: ["3-1-1", "5-5-1", "5-5-2", "5-5-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "fixed", id: "sth-1-2", name: "器械推举", ename: "Shoulder Press", 
		pim: ["4-1", "4-2", "4-3"], min: ["5-5-1", "5-5-2", "5-5-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-3", name: "杠铃推举", ename: "", 
		pim: ["4-1", "4-2", "4-3"], min: ["5-5-1", "5-5-2", "5-5-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-4", name: "颈后推举", ename: "", 
		pim: ["4-1", "4-2"], min: ["5-5-1", "5-5-2", "5-5-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-5", name: "哑铃推举", ename: "", 
		pim: ["4-1", "4-2"], min: ["5-5-1", "5-5-2", "5-5-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-6", name: "站姿侧平举", ename: "", 
		pim: ["4-2"], min: ["4-1", "4-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "fixed", id: "sth-1-7", name: "站姿拉力器单臂交叉侧平拉", ename: "", 
		pim: ["4-2"], min: ["4-1", "4-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "fixed", id: "sth-1-8", name: "站姿拉力器单臂侧平拉", ename: "", 
		pim: ["4-2"], min: ["4-1", "4-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "fixed", id: "sth-1-8", name: "坐姿拉力器单臂交叉侧平拉", ename: "", 
		pim: ["4-2"], min: ["4-1", "4-3"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-9", name: "反握哑铃过顶侧平举", ename: "", 
		pim: ["4-2", "4-3"], min: ["3-1-1"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-10", name: "哑铃前平举", ename: "", 
		pim: ["4-1"], min: ["3-1-1"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-11", name: "坐姿俯身哑铃侧平举", ename: "", 
		pim: ["4-3"], min: [], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-12", name: "站姿俯身哑铃侧平举", ename: "", 
		pim: ["4-3"], min: [], ext: []},
	{part: "肩部", epart: "Shoulder", type: "fixed", id: "sth-1-13", name: "俯身拉力器侧平拉", ename: "", 
		pim: ["4-3"], min: [], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-14", name: "侧卧侧平举", ename: "", 
		pim: ["4-2", "4-3"], min: [], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-15", name: "直立划船", ename: "", 
		pim: ["3-1-1","4-1"], min: ["4-2", "2-1", "2-2", "5-2-1", "5-2-2"], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-16", name: "哑铃耸肩", ename: "", 
		pim: ["3-1-1"], min: [], ext: []},
	{part: "肩部", epart: "Shoulder", type: "free", id: "sth-1-17", name: "杠铃耸肩", ename: "", 
		pim: ["3-1-1"], min: [], ext: []},

	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-1", name: "", ename: "Chest Press", 
		pim: ["2-1", "2-2"], min: ["4-1", "4-2", "5-5-1", "5-5-2", "5-5-3"], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-2", name: "", ename: "Wide Chest Press", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-3", name: "", ename: "Incline Chest Press", 
		pim: ["2-1", "2-2", "4-1", "5-5-1", "5-5-2", "5-5-3"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-4", name: "仰卧杠铃推举", ename: "", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3"], min: ["4-1"], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-5", name: "上斜杠铃推举", ename: "", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3", "4-1"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-6", name: "仰卧器械推举", ename: "", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3"], min: ["4-1"], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-7", name: "上斜器械推举", ename: "", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3", "4-1"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-8", name: "下斜器械推举", ename: "", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3"], min: ["4-1"], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-9", name: "仰卧哑铃推举", ename: "", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3"], min: ["4-1"], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-10", name: "上斜哑铃推举", ename: "", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3", "4-1"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-11", name: "下斜哑铃推举", ename: "", 
		pim: ["2-1", "2-2", "5-5-1", "5-5-2", "5-5-3"], min: ["4-1"], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-12", name: "哑铃飞鸟", ename: "", 
		pim: ["2-1", "2-2"], min: ["4-1"], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-13", name: "上斜哑铃飞鸟", ename: "", 
		pim: ["2-2"], min: ["4-1"], ext: ["2-1"]},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-14", name: "器械飞鸟", ename: "", 
		pim: ["2-1", "2-2"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-15", name: "站姿拉力器夹胸", ename: "", 
		pim: ["2-1", "2-2"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-16", name: "前侧拉力器夹胸", ename: "", 
		pim: ["2-2"], min: ["4-1"], ext: ["2-1"]},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-17", name: "仰卧拉力器夹胸", ename: "", 
		pim: ["2-1", "2-2"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-18", name: "双杠屈臂撑", ename: "", 
		pim: ["2-1", "2-2"], min: ["5-5-1", "5-5-2", "5-5-3"], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-19", name: "仰握直臂上拉", ename: "", 
		pim: ["2-1", "2-2", "2-3"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-20", name: "器械上拉", ename: "", 
		pim: ["2-3", "3-6"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-21", name: "绳索下拉", ename: "", 
		pim: ["2-3"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-22", name: "单臂拉力器下拉", ename: "", 
		pim: ["2-3"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-23", name: "窄握引体向上", ename: "", 
		pim: ["2-3"], min: [], ext: []},
	{part: "胸部", epart: "Chest", type: "free", id: "sth-2-24", name: "悬垂前锯肌卷腹", ename: "", 
		pim: ["2-3"], min: [], ext: []},
	{part: "胸部", epart: "Back", type: "fixed", id: "sth-2-25", name: "窄握高位下拉", ename: "", 
		pim: ["2-3"], min: [], ext: []},




	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-1", name: "低位划船", ename: "Low Row", 
		pim: ["3-6"], min: ["3-1-1", "3-1-2", "5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-2", name: "高位下拉", ename: "", 
		pim: ["3-6", "5-2-1", "5-2-2"], min: [], ext: []},
	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-3", name: "划船", ename: "Row", 
		pim: ["3-1-1", "3-1-2", "4-3", "3-6", "5-2-1", "5-2-2"], min: [], ext: []},

	{part: "腰部", epart: "Waist", type: "fixed", id: "sth-4-1", name: "", ename: "Lower Back", 
		pim: ["8-1"], min: [], ext: []},

	{part: "大臂", epart: "Upper Arms", type: "fixed", id: "sth-5-1", name: "", ename: "Arm Curl", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},


	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-1", name: "", ename: "Leg Press", 
		pim: ["a-6", "a-7", "a-8", "9-1"], min: ["a-9-2", "a-9-3", "a-1", "a-5"], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-2", name: "", ename: "Adductor", 
		pim: ["a-1", "a-5"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-3", name: "", ename: "Leg Extension", 
		pim: ["a-6", "a-7", "a-8"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-4", name: "", ename: "Abductor", 
		pim: ["9-1"], min: [], ext: []}

	// {part: "肩部", epart: "Shoulder", type: "fixed", id: "sth-1-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "背部", epart: "Back", type: "fixed", id: "sth-3-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "腰部", epart: "Waist", type: "fixed", id: "sth-4-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "大臂", epart: "Upper Arms", type: "fixed", id: "sth-5-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "前臂", epart: "Forearms", type: "fixed", id: "sth-6-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "前臂", epart: "Forearms", type: "fixed", id: "sth-6-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "小腿", epart: "Calves", type: "fixed", id: "sth-8-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "小腿", epart: "Calves", type: "fixed", id: "sth-8-", name: "", ename: "", pim: [], min: [], ext: []},
	// {part: "腹部", epart: "Abs", type: "fixed", id: "sth-9-", name: "", ename: "", pim: [], min: [], ext: []}
	// {part: "腹部", epart: "Abs", type: "fixed", id: "sth-9-", name: "", ename: "", pim: [], min: [], ext: []}

];

/**
 * 把json数据转入Map中
 */
workoutApp.workout.addItems2Map = function (item, map) {
	for (var i in item) {
		var d = item[i];
		map.put(d.id, d);
	}
	return map;
};

workoutApp.workout.StrengthItemMap = workoutApp.workout.addItems2Map(
	workoutApp.workout.StrengthItems, new jadeUtils.dataStructure.Map());

workoutApp.workout.AerobicItemMap = workoutApp.workout.addItems2Map(
	workoutApp.workout.AerobicItems, new jadeUtils.dataStructure.Map());



workoutApp.workoutRec = {};

workoutApp.workoutRec.recordStrengthRec = function (
		username, password, workoutId, weight, repeat, successCallback) 
{
	var auth = 'Basic ' + jadeUtils.string.base64encode(
			jadeUtils.string.utf16to8(username + ':' + password)); 
	if ("" !== username) {
		$.ajax({ type: 'POST', dataType: 'json', timeout: workoutApp.connTimeout,
				url: workoutApp.appPath + '/api/workout/recordStrengthRec', 
				headers: {Authorization: auth},
				data: {
					username: username,
					workoutId: workoutId,
					weight  : weight,
					repeat  : repeat},
				success: function(data, status, xhr) {
					successCallback(data, status, xhr);
				},
				error: function(xhr, errorType, error) { alert("Ajax Error!"); },
				complete: function(xhr, status) {}
			});
	}
};


workoutApp.workoutRec.recordAerobicRec = function (
		username, password, workoutId, time, distance, calories, successCallback)
{
	var auth = 'Basic ' + jadeUtils.string.base64encode(
			jadeUtils.string.utf16to8(username + ':' + password)); 
	if ("" !== username) {
		$.ajax({ type: 'POST', dataType: 'json', timeout: workoutApp.connTimeout,
				url: workoutApp.appPath + '/api/workout/recordAerobicRec', 
				headers: {Authorization: auth},
				data: {
					username: username,
					workoutId: workoutId,
					time: time,
					distance: distance,
					calories: calories},
				success: function(data, status, xhr) {
					successCallback(data, status, xhr);
				},
				error: function(xhr, errorType, error) { alert("Ajax Error!"); },
				complete: function(xhr, status) {}
			});
	}
};



workoutApp.workoutRec.findAerobicRec = function (
		username, password, workoutId, logTimeFloor, logTimeCeil, 
		callbackSuccess, callbackFail, callbackError)
{
	var auth = 'Basic ' + jadeUtils.string.base64encode(
			jadeUtils.string.utf16to8(username + ':' + password)); 
	if ("" !== username) {
		$.ajax({ type: 'POST', dataType: 'json', timeout: workoutApp.connTimeout,
				url: workoutApp.appPath + '/api/workout/findAerobicRec', 
				headers: {Authorization: auth},
				data: {
					username: username,
					workoutId: workoutId,
					logTimeFloor: logTimeFloor,
					logTimeCeil: logTimeCeil
				},
				success: function(data, status, xhr) {
					console.debug(data);
					callbackSuccess(data);
				},
				error: function(xhr, errorType, error) { alert("Ajax Error!"); },
				complete: function(xhr, status) {}
			});
	}
};


workoutApp.workoutRec.findStrengthRec = function (
		username, password, workoutId, logTimeFloor, logTimeCeil, 
		callbackSuccess, callbackFail, callbackError)
{
	var auth = 'Basic ' + jadeUtils.string.base64encode(
			jadeUtils.string.utf16to8(username + ':' + password)); 
	if ("" !== username) {
		$.ajax({ type: 'POST', dataType: 'json', timeout: workoutApp.connTimeout,
				url: workoutApp.appPath + '/api/workout/findStrengthRec', 
				headers: {Authorization: auth},
				data: {
					username: username,
					workoutId: workoutId,
					logTimeFloor: logTimeFloor,
					logTimeCeil: logTimeCeil
				},
				success: function(data, status, xhr) {
					console.debug(data);
					callbackSuccess(data);
				},
				error: function(xhr, errorType, error) { alert("Ajax Error!"); },
				complete: function(xhr, status) {}
			});
	}
};

workoutApp.workoutRec.renderAerobicRecDetailPage = function (data) {
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
};


workoutApp.workoutRec.renderStrengthRecDetailPage = function (data) {
	var html = "";
	$.each(data.result, function (idx, item) {
		console.debug(item);
		var t = new Date();
		t.setTime(item.logTime);
		html = html + '<li>' + jadeUtils.time.getLocalTimeStr(t) + 
		'<ul><li>Weight: ' + item.weight + 
		'</li><li>Repeat: ' + item.repeat + 
		'</li></ul></li>';
	});
	$('#historyRec').html(html);
};
