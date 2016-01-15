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
	{part: "胸部", epart: "Chest", type: "fixed", id: "sth-2-25", name: "窄握高位下拉", ename: "", 
		pim: ["3-6", "2-3"], min: ["5-2-1", "5-2-2"], ext: []},

	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-1", name: "器械划船", ename: "Row", 
		pim: ["3-1-1", "3-1-2", "4-3", "3-6", "5-2-1", "5-2-2"], min: [], ext: []},
	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-3", name: "器械低位划船", ename: "Low Row", 
		pim: ["3-6"], min: ["3-1-1", "3-1-2", "5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-2", name: "宽握高位下拉", ename: "", 
		pim: ["3-6", "5-2-1", "5-2-2"], min: [], ext: []},
	{part: "背部", epart: "Back", type: "free", id: "sth-3-4", name: "宽握颈后引体向上", ename: "", 
		pim: ["3-6", "5-2-1", "5-2-2"], min: [], ext: []},
	{part: "背部", epart: "Back", type: "free", id: "sth-3-5", name: "宽握正面引体向上", ename: "", 
		pim: ["3-6", "5-2-1", "5-2-2"], min: [], ext: []},
	{part: "背部", epart: "Back", type: "free", id: "sth-3-6", name: "窄握引体向上", ename: "", 
		pim: ["3-6"], min: ["5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "free", id: "sth-3-7", name: "俯身杠铃划船", ename: "", 
		pim: ["3-6"], min: ["5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "free", id: "sth-3-8", name: "俯身哑铃划船", ename: "", 
		pim: ["3-6"], min: ["5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "free", id: "sth-3-9", name: "T杠划船", ename: "", 
		pim: ["3-6"], min: ["5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "free", id: "sth-3-10", name: "单臂哑铃划船", ename: "", 
		pim: ["3-6"], min: ["5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-11", name: "单臂拉力器划船", ename: "", 
		pim: ["3-6"], min: ["5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-12", name: "坐姿拉力器划船", ename: "", 
		pim: ["3-6"], min: ["5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "free", id: "sth-3-13", name: "屈臂杠铃上拉", ename: "", 
		pim: ["3-6", "2-3"], min: ["5-2-1", "5-2-2"], ext: []},
	{part: "背部", epart: "Back", type: "fixed", id: "sth-3-14", name: "器械上拉", ename: "", 
		pim: ["3-6", "2-3"], min: [], ext: []},

	{part: "腰部", epart: "Waist", type: "free", id: "sth-4-1", name: "硬拉", ename: "", 
		pim: ["3-6", "2-3", "8-1", "9-1", "9-2"], min: [], ext: []},
	{part: "腰部", epart: "Waist", type: "free", id: "sth-4-2", name: "负重体前屈", ename: "", 
		pim: ["8-1"], min: [], ext: []},
	{part: "腰部", epart: "Waist", type: "free", id: "sth-4-3", name: "俯卧挺身", ename: "", 
		pim: ["8-1"], min: [], ext: []},

	{part: "大臂", epart: "Upper Arms", type: "fixed", id: "sth-5-1", name: "器械弯举", ename: "Arm Curl", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-2", name: "站姿杠铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-3", name: "斜托杠铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-4", name: "斜托哑铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-5", name: "上斜哑铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-6", name: "坐姿哑铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-7", name: "集中哑铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-8", name: "仰卧哑铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-9", name: "双手拉力器弯举", ename: "", 
		pim: ["5-2-1", "5-2-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-10", name: "站姿反握杠铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2","6-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-11", name: "反握斜托杠铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2","6-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-12", name: "反握斜托哑铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2","6-2"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "fixed", id: "sth-5-13", name: "拉力器下压", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "fixed", id: "sth-5-14", name: "单臂拉力器反握下压", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-15", name: "坐姿臂屈伸", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-16", name: "站姿臂屈伸", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-17", name: "窄握推举", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: ["2-1", "2-2"], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-18", name: "仰卧杠铃臂屈伸", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-19", name: "仰卧哑铃臂屈伸", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-20", name: "俯身哑铃臂屈伸", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-21", name: "单臂臂屈伸", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-22", name: "屈臂撑", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-23", name: "背后屈臂撑", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},
	{part: "大臂", epart: "Upper Arms", type: "free", id: "sth-5-24", name: "固定杠屈臂撑", ename: "", 
		pim: ["5-5-1", "5-5-2","5-5-3"], min: [], ext: []},

	{part: "前臂", epart: "Forme Arms", type: "free", id: "sth-6-1", name: "杠铃腕弯举", ename: "", 
		pim: ["6-2"], min: [], ext: []},
	{part: "前臂", epart: "Forme Arms", type: "free", id: "sth-6-2", name: "单臂哑铃腕弯举", ename: "", 
		pim: ["6-2"], min: [], ext: []},
	{part: "前臂", epart: "Forme Arms", type: "free", id: "sth-6-3", name: "背后杠铃腕弯举", ename: "", 
		pim: ["6-2"], min: [], ext: []},
	{part: "前臂", epart: "Forme Arms", type: "free", id: "sth-6-4", name: "反握杠铃腕弯举", ename: "", 
		pim: ["6-1"], min: [], ext: []},
	{part: "前臂", epart: "Forme Arms", type: "free", id: "sth-6-5", name: "反握哑铃腕弯举", ename: "", 
		pim: ["6-1"], min: [], ext: []},
	{part: "前臂", epart: "Forme Arms", type: "free", id: "sth-6-6", name: "反握杠铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2", "6-1", "6-3"], min: [], ext: []},
	{part: "前臂", epart: "Forme Arms", type: "free", id: "sth-6-7", name: "反握斜托杠铃弯举", ename: "", 
		pim: ["5-2-1", "5-2-2", "6-1", "6-3"], min: [], ext: []},
	{part: "前臂", epart: "Forme Arms", type: "fixed", id: "sth-6-7", name: "反握器械弯举", ename: "", 
		pim: ["5-2-1", "5-2-2", "6-1", "6-3"], min: [], ext: []},
	{part: "前臂", epart: "Forme Arms", type: "fixed", id: "sth-6-7", name: "单臂反握拉力器弯举", ename: "", 
		pim: ["5-2-1", "5-2-2", "6-1", "6-3"], min: [], ext: []},

	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-1", name: "", ename: "Leg Press", 
		pim: ["a-6", "a-7", "a-8", "9-1", "9-2"], min: ["a-9-2", "a-9-3", "a-1", "a-5"], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-14", name: "", ename: "Leg Press", 
		pim: ["a-6", "a-7", "a-8", "9-1", "9-2"], min: ["a-9-2", "a-9-3", "a-1", "a-5"], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-2", name: "", ename: "Adductor", 
		pim: ["a-1", "a-5"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-3", name: "腿屈伸", ename: "Leg Extension", 
		pim: ["a-6", "a-7", "a-8"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-4", name: "", ename: "Abductor", 
		pim: ["9-1"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "free", id: "sth-7-5", name: "深蹲", ename: "Leg Press", 
		pim: ["a-6", "a-7", "a-8", "8-1", "9-1", "9-2"], min: ["a-9-2", "a-9-3", "a-1", "a-5"], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-6", name: "器械深蹲", ename: "Leg Press", 
		pim: ["a-6", "a-7", "a-8", "8-1", "9-1", "9-2"], min: ["a-9-2", "a-9-3", "a-1", "a-5"], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-7", name: "腿举", ename: "", 
		pim: ["a-6", "a-7", "a-8", "9-1", "9-2"], min: ["a-9-2", "a-9-3", "a-1", "a-5"], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-8", name: "弓步", ename: "", 
		pim: ["a-6", "a-7", "a-8", "9-1", "9-2"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-9", name: "腿弯举", ename: "", 
		pim: ["a-9-2", "a-9-3", "a-9-4"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "fixed", id: "sth-7-10", name: "站姿腿弯举", ename: "", 
		pim: ["a-9-2", "a-9-3", "a-9-4"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "free", id: "sth-7-11", name: "直腿硬拉", ename: "", 
		pim: ["a-9-2", "a-9-3", "a-9-4", "8-1", "9-1", "9-2"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "free", id: "sth-7-12", name: "跪姿后踢腿", ename: "", 
		pim: ["9-1", "9-2"], min: [], ext: []},
	{part: "大腿", epart: "Thighs", type: "free", id: "sth-7-13", name: "背后剪腿", ename: "", 
		pim: ["9-1", "9-2"], min: [], ext: []},

	{part: "小腿", epart: "Calves", type: "fixed", id: "sth-8-1", name: "站姿提踵", ename: "", 
		pim: ["b-6-1", "b-6-2", "b-7-1", "b-7-2"], min: [], ext: []},

	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-1", name: "卷腹", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-2", name: "转体卷腹", ename: "", 
		pim: ["7-1", "7-5"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-3", name: "反向卷腹", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-4", name: "悬垂卷腹", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-4", name: "悬垂抬腿", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-4", name: "悬垂转体抬腿", ename: "", 
		pim: ["7-1", "7-5"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "fixed", id: "sth-9-5", name: "器械卷腹", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "fixed", id: "sth-9-6", name: "拉力器卷腹", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-7", name: "坐姿屈膝上举", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-8", name: "坐姿转体", ename: "", 
		pim: ["7-5"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-9", name: "俯身转体", ename: "", 
		pim: ["7-5"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-10", name: "仰卧抬腿", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-11", name: "仰卧屈膝抬腿", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-12", name: "上斜屈膝抬腿", ename: "", 
		pim: ["7-1"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-12", name: "侧卧侧抬腿", ename: "", 
		pim: ["7-4", "7-5"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-12", name: "侧卧屈膝侧抬腿", ename: "", 
		pim: ["7-4", "7-5"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-12", name: "侧卧前踢腿", ename: "", 
		pim: ["7-4", "7-5"], min: [], ext: []},
	{part: "腹部", epart: "Abs", type: "free", id: "sth-9-12", name: "完全吸腹", ename: "", 
		pim: ["7-1"], min: [], ext: []}
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

workoutApp.workoutRec.showStrengthItems = function () {
	var shoulder1 = [];
	var chest1 = [];
	var back1 = [];
	var waist1 = [];
	var upperarm1 = [];
	var formearm1 = [];
	var thigh1 = [];
	var calve1 = [];
	var abs1 = [];

	var shoulder2 = [];
	var chest2 = [];
	var back2 = [];
	var waist2 = [];
	var upperarm2 = [];
	var formearm2 = [];
	var thigh2 = [];
	var calve2 = [];
	var abs2 = [];

	$.each(workoutApp.workout.StrengthItems, function(index, item) {
			if ("Shoulder" == item.epart && "fixed" == item.type) {
				shoulder1.push(item);
			} else if ("Shoulder" == item.epart && "free" == item.type) {
				shoulder2.push(item);
			} else if ("Chest" == item.epart && "fixed" == item.type) {
				chest1.push(item);
			} else if ("Chest" == item.epart && "free" == item.type) {
				chest2.push(item);
			} else if ("Back" == item.epart && "fixed" == item.type) {
				back1.push(item);
			} else if ("Back" == item.epart && "free" == item.type) {
				back2.push(item);
			} else if ("Waist" == item.epart && "fixed" == item.type) {
				waist1.push(item);
			} else if ("Waist" == item.epart && "free" == item.type) {
				waist2.push(item);
			} else if ("Upper Arms" == item.epart && "fixed" == item.type) {
				upperarm1.push(item);
			} else if ("Upper Arms" == item.epart && "free" == item.type) {
				upperarm2.push(item);
			} else if ("Forme Arms" == item.epart && "fixed" == item.type) {
				formearm1.push(item);
			} else if ("Forme Arms" == item.epart && "free" == item.type) {
				formearm2.push(item);
			} else if ("Thighs" == item.epart && "fixed" == item.type) {
				thigh1.push(item);
			} else if ("Thighs" == item.epart && "free" == item.type) {
				thigh2.push(item);
			} else if ("Calves" == item.epart && "fixed" == item.type) {
				calve1.push(item);
			} else if ("Calves" == item.epart && "free" == item.type) {
				calve2.push(item);
			} else if ("Abs" == item.epart && "fixed" == item.type) {
				abs1.push(item);
			} else if ("Abs" == item.epart && "free" == item.type) {
				abs2.push(item);
			}
	});

	var addFunc = function (itemlist, listId) {
		var html = '<ul class="list-unstyled">';
		$.each(itemlist, function(index, item) {
				html = html + '<li><img class="img-w-lst" src="' + $("#cdnworkout").val() + 
				'images/workout/' + item.id + '.svg" /><em class="lst-ipt">' + 
				item.name + '</em><em class="lst-ipt">(' + item.ename + ')</em><em>' + 
				'<input type="button" item="' + item.id + '" value="record" class="btn btn-default go-detail" /></em></li>';
				});
		$("#" + listId).html(html + '</ul>');
		// $("#tit-" + listId).on("click", function(e) {
		// 		$(".dtlitms").attr("style", "display:none");
		// 		$("#" + listId).attr("style", "display:block");
		// 		});
	};

	addFunc(shoulder1, "shoulder1");
	addFunc(chest1   , "chest1");
	addFunc(back1    , "back1");
	addFunc(waist1   , "waist1");
	addFunc(upperarm1, "upperarm1");
	addFunc(formearm1, "formearm1");
	addFunc(thigh1   , "thigh1");
	addFunc(calve1   , "calve1");
	addFunc(abs1     , "abs1");
	addFunc(shoulder2, "shoulder2");
	addFunc(chest2   , "chest2");
	addFunc(back2    , "back2");
	addFunc(waist2   , "waist2");
	addFunc(upperarm2, "upperarm2");
	addFunc(formearm2, "formearm2");
	addFunc(thigh2   , "thigh2");
	addFunc(calve2   , "calve2");
	addFunc(abs2     , "abs2");

	$(".go-detail").each(function (idx, item) {
			$(item).on("click", function (e) {
				jadeUtils.url.goUrl(workoutApp.appPath + 
					"/page/workout/strengthWorkout/details/" + $(this).attr("item"));
				});
			});
};


workoutApp.workoutRec.showAeroboicItems = function () {
	var html = '';
	$.each(workoutApp.workout.AerobicItems, function(index, item) {
		html = html + '<li><img class="img-w-lst" src="' + $("#cdnworkout").val() + 
			'images/workout/' + item.id + '.svg" /><em class="lst-ipt">' + 
		 	item.name + '</em><em class="lst-ipt">(' + item.ename + ')</em><em>' + 
		 	'<input type="button" item="' + item.id + '" value="record" class="btn btn-default go-detail" /></em></li>';
	});
	$("#workoutinfo").html(html);

	$(".go-detail").each(function (idx, item) {
			$(item).on("click", function (e) {
				jadeUtils.url.goUrl(workoutApp.appPath + 
					"/page/workout/aerobicWorkout/details/" + $(this).attr("item"));
				});
			});
};



workoutApp.workoutRec.recordStrengthRec = function (
		username, password, workoutId, weight, repeat, logTime, successCallback) 
{
	var auth = 'Basic ' + jadeUtils.string.base64encode(
			jadeUtils.string.utf16to8(username + ':' + password)); 
	if ("" !== username) {
		$.ajax({ type: 'POST', dataType: 'json', timeout: workoutApp.connTimeout,
				url: workoutApp.appPath + '/api/workout/recordStrengthRec', 
				headers: {Authorization: auth},
				data: {
					username: username,
					logTime : logTime,
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
		username, password, workoutId, time, distance, calories, logTime, 
		successCallback)
{
	var auth = 'Basic ' + jadeUtils.string.base64encode(
			jadeUtils.string.utf16to8(username + ':' + password)); 
	if ("" !== username) {
		$.ajax({ type: 'POST', dataType: 'json', timeout: workoutApp.connTimeout,
				url: workoutApp.appPath + '/api/workout/recordAerobicRec', 
				headers: {Authorization: auth},
				data: {
					username : username,
					logTime  : logTime,
					workoutId: workoutId,
					time     : time,
					distance : distance,
					calories : calories},
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

workoutApp.workoutRec.renderStrengthRecDetailPage = function (data) {
	var recMap = new jadeUtils.dataStructure.Map();

	$.each(data.result, function (idx, item) {
		if (idx > maxIndex) {maxIndex = idx;}
		console.debug(item);
		var t = new Date();
		t.setTime(item.logTime);
		var timeString = jadeUtils.time.getLocalTimeStr(t);
		var key = timeString.split(" ")[0];
		var recArr = [];
		if (recMap.containsKey(key)) { recArr = recMap.get(key); } 
		else { recMap.put(key, recArr); }
		recArr.push(item);
	});

	var maxIndex = 0;
	for (var i = 0; i < recMap.size(); i++) 
	{ if (recMap.element(i).value.length > maxIndex) 
		{maxIndex = recMap.element(i).value.length;} }

	var titles = "";
	for (i = 0; i < recMap.size(); i++) 
	{ titles = titles + "<th>" + recMap.element(i).key + "</th>"; }
	$('#recTitles').html(titles);

	var recs = "";
	for (var l = 0; l <= maxIndex; l++) {
		recs = recs + '<tr>';
		for (i = 0; i < recMap.size(); i++) {
			recs = recs + '<td>';
			var eArr = recMap.element(i).value;
			if (l < eArr.length) 
			{ var e = eArr[l]; recs = recs + e.weight + " kg * " + e.repeat; }
			recs = recs + '</td>';
		}
		recs = recs + '</tr>';
	}
	$('#recs').html(recs);
};

workoutApp.workoutRec.renderAerobicRecDetailPage = function (data) {
	var recMap = new jadeUtils.dataStructure.Map();

	$.each(data.result, function (idx, item) {
		console.debug(item);
		var t = new Date();
		t.setTime(item.logTime);
		var timeString = jadeUtils.time.getLocalTimeStr(t);
		var key = timeString.split(" ")[0];
		var recArr = [];
		if (recMap.containsKey(key)) { recArr = recMap.get(key); } 
		else { recMap.put(key, recArr); }
		recArr.push(item);
	});

	var maxIndex = 0;
	for (var i = 0; i < recMap.size(); i++) 
	{ if (recMap.element(i).value.length > maxIndex) 
		{maxIndex = recMap.element(i).value.length;} }

	var titles = "";
	for (i = 0; i < recMap.size(); i++) 
	{ titles = titles + "<th>" + recMap.element(i).key + "</th>"; }
	$('#recTitles').html(titles);

	var recs = "";
	for (var l = 0; l <= maxIndex; l++) {
		recs = recs + '<tr>';
		for (i = 0; i < recMap.size(); i++) {
			recs = recs + '<td>';
			var eArr = recMap.element(i).value;
			if (l < eArr.length) { 
				var e = eArr[l]; 
				recs = recs + e.time + " mins " + e.distance + " km " + e.calories +" cals" ;
			}
			recs = recs + '</td>';
		}
		recs = recs + '</tr>';
	}
	$('#recs').html(recs);
};



