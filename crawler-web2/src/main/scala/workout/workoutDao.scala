package net.jadedungeon.workout

import jadeutils.common.Logging

import scala.collection.JavaConversions._

import jadeutils.mongo.MongoServer
import jadeutils.mongo.BaseMongoDao
import jadeutils.mongo.Condition.LinkType.AND
import jadeutils.mongo.Condition.Option.GTE
import jadeutils.mongo.Condition.Option.LT
import jadeutils.mongo.Condition.newCondition

class UserAuthDao(serverList: java.util.List[MongoServer]) 
extends BaseMongoDao[UserAuth](serverList) with Logging 
{
	def this(host: String, port: Int, auth: List[Array[String]]) = this(new MongoServer(host, port, auth) :: Nil)

	/* {user:"user2",item:"strength8",logTime:{$gte:1442304625000, $lt:1442304630000}} */
	def findAuth(user: String): List[UserAuth] = {
		val cdt = newCondition("username", user)

		this.findByCondition(cdt).toList.toList
	}
}



class AerobicRecordDao(serverList: java.util.List[MongoServer]) 
extends BaseMongoDao[AerobicRecord](serverList) with Logging 
{
	def this(host: String, port: Int, auth: List[Array[String]]) = this(new MongoServer(host, port, auth) :: Nil)

	/* {user:"user2",item:"strength8",logTime:{$gte:1442304625000, $lt:1442304630000}} */
	def findAerobicRecs(user: String, item: String, logTimeFloor: Long, logTimeCeil: Long): 
	List[AerobicRecord] = 
	{
		logDebug(
			"findAerobicRecs, user: {}, item: {}, logTimeFloor: {}, logTimeCeil: {}", 
			user, item, logTimeFloor, logTimeCeil)
		val cu = newCondition("user", user)
		val ct = newCondition("logTime", newCondition(GTE,logTimeFloor).append(
			AND, newCondition(LT, logTimeCeil))).append(AND, cu)
		val cdt = if (null == item) ct else ct.append(AND, newCondition("item", item))

		this.findByCondition(cdt).sort(newCondition("logTime",-1)).toList.toList
	}
}

class StrengthRecordDao(serverList: java.util.List[MongoServer]) 
extends BaseMongoDao[StrengthRecord](serverList) with Logging 
{

	def this(host: String, port: Int, auth: List[Array[String]]) = this(new MongoServer(host, port, auth) :: Nil)

	/* {user:"user2",item:"strength8",logTime:{$gte:1442304625000, $lt:1442304630000}} */
	def findStrengthRecs(user: String, item: String, logTimeFloor: Long, logTimeCeil: Long): 
	List[StrengthRecord] = 
	{
		logDebug(
			"findStrengthRecs, user: {}, item: {}, logTimeFloor: {}, logTimeCeil: {}", 
			user, item, logTimeFloor, logTimeCeil)
		val cu = newCondition("user", user)
		val ct = newCondition("logTime", newCondition(GTE,logTimeFloor).append(
			AND, newCondition(LT, logTimeCeil))).append(AND, cu)
		val cdt = if (null == item) ct else ct.append(AND, newCondition("item", item))

		this.findByCondition(ct).sort(newCondition("logTime",-1)).toList.toList
	}

}
