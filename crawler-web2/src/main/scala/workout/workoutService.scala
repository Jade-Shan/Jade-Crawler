package net.jadedungeon.workout

import jadeutils.common.Logging

trait workoutRecService {
	this: WorkoutRecDaoComponent =>

	def findAerobicRecs(user: String, item: String, logTimeFloor: Long, logTimeCeil: Long): 
	List[AerobicRecord] = 
	{
		this.RecDaos.aerobicRecordDao.findAerobicRecs(user, item, logTimeFloor, logTimeCeil)
	}

	def findStrengthRecs(user: String, item: String, logTimeFloor: Long, logTimeCeil: Long): 
	List[StrengthRecord] = 
	{
		this.RecDaos.strengthRecordDao.findStrengthRecs(user, item, logTimeFloor, logTimeCeil)
	}
}
