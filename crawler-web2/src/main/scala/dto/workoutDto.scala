package jadecrawler.web2

import jadeutils.mongo._

@MongoDocument(databaseName="workout", collectionName="aerobicrecord")
case class AerobicRecord(
	usr: String, itm: String, tim: Int, dst: Int, clrs: Int) extends MongoModel
{
	def this() = this(null, null, 0, 0, 0)

	@MongoField var user = usr         /* 用户 */
	@MongoField var item = itm         /* 运动项目 */
	@MongoField var time = tim         /* 时间或次数 */
	@MongoField var distance = dst     /* 距离 */
	@MongoField var calories = clrs    /* 消耗的热量 */

	override def toString = ("""AerobicRecord: {user: "%s",item: "%s",""" +
		"time: %d, distance: %d, calories: %d}").format(
		user, item, time, distance, calories)

}

@MongoDocument(databaseName="workout", collectionName="strengthrecord")
case class StrengthRecord(
	usr: String, itm: String, wet: Int, tim: Int) extends MongoModel
{
	def this() = this(null, null, 0, 0)

	@MongoField var user = usr       /* 用户 */
	@MongoField var item = itm       /* 运动项目 */
	@MongoField var weight = wet     /* 重量 */
	@MongoField var time = tim       /* 时间或次数 */

	override def toString = ("""StrengthRecord: {""" + 
		"""user: "%s", item: "%s", weight: %d, time: %d}""").format (
		user, item, weight, time)

}

