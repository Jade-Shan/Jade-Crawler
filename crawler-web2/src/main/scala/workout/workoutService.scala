package net.jadedungeon.workout

import jadeutils.common.Logging

trait workoutRecService {
	this: DaoComponent =>

	def findAerobicRecs(user: String, item: String, logTimeFloor: Long, logTimeCeil: Long): 
	List[AerobicRecord] = 
	{
		this.DaoCtx.aerobicRecordDao.findAerobicRecs(user, item, logTimeFloor, logTimeCeil)
	}

	def findStrengthRecs(user: String, item: String, logTimeFloor: Long, logTimeCeil: Long): 
	List[StrengthRecord] = 
	{
		this.DaoCtx.strengthRecordDao.findStrengthRecs(user, item, logTimeFloor, logTimeCeil)
	}
}
