package net.jadedungeon.workout

import jadeutils.mongo._

@MongoDocument(databaseName="jadedungeon", collectionName="userauth")
case class UserAuth(
	usr: String, pwd: String, ts: Long, upt: Long) extends MongoModel
{
	def this() = this(null, null, System.currentTimeMillis, 0)

	@MongoField var username = usr
	@MongoField var password = pwd
	@MongoField var createTime = ts
	@MongoField var lastUpdateTime = ts

	override def toString = ("""StrengthRecord: {""" + 
		"""user: "%s", password: "%s", createTime: %d, lastUpdateTime: %d}""").format (
		username, password, createTime, lastUpdateTime)

}


@MongoDocument(databaseName="workout", collectionName="aerobicrecord")
case class AerobicRecord(
	usr: String, itm: String, tim: Int, dst: Double, clrs: Int, ts: Long) extends MongoModel
{
	def this() = this(null, null, 0, 0, 0, 0)

	@MongoField var user = usr         /* 用户 */
	@MongoField var item = itm         /* 运动项目 */
	@MongoField var time = tim         /* 时间或次数 */
	@MongoField var distance = dst     /* 距离 */
	@MongoField var calories = clrs    /* 消耗的热量 */
	@MongoField var logTime = ts

	override def toString = ("""AerobicRecord: {user: "%s",item: "%s",""" +
		"time: %d, distance: %f, calories: %d, logTime: %d}").format(
		user, item, time, distance, calories, logTime)

}

@MongoDocument(databaseName="workout", collectionName="strengthrecord")
case class StrengthRecord(
	usr: String, itm: String, wet: Double, rpt: Int, ts: Long) extends MongoModel
{
	def this() = this(null, null, 0, 0, 0)

	@MongoField var user = usr       /* 用户 */
	@MongoField var item = itm       /* 运动项目 */
	@MongoField var weight = wet     /* 重量 */
	@MongoField var repeat = rpt       /* 时间或次数 */
	@MongoField var logTime = ts

	override def toString = ("""StrengthRecord: {""" + 
		"""user: "%s", item: "%s", weight: %f, repeat: %d, logTime: %d}""").format (
		user, item, weight, repeat, logTime)

}
