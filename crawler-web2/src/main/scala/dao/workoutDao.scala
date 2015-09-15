package jadecrawler.web2

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import scala.collection.JavaConversions._

import jadeutils.mongo.MongoServer
import jadeutils.mongo.BaseMongoDao
import jadeutils.mongo.Condition.LinkType.AND
import jadeutils.mongo.Condition.Option.GTE
import jadeutils.mongo.Condition.Option.LT
import jadeutils.mongo.Condition.newCondition

class AerobicRecordDao(serverList: java.util.List[MongoServer]) extends BaseMongoDao[AerobicRecord](serverList) {
	val logger = AerobicRecordDao.logger

	def this(server: (String, Int)) = this(new MongoServer(server._1, server._2) :: Nil)

	/**
		* {user:"user2",item:"strength8",logTime:{$gte:1442304625000, $lt:1442304630000}}
		*/
	def findItems(
		user: String, item: String, logTimeFloor: Long, logTimeCeil: Long
	): List[AerobicRecord]= {
		val cu = newCondition("user", user)
		val ct = newCondition("logTime", newCondition(GTE,logTimeFloor).append(
			AND, newCondition(LT, logTimeCeil))).append(AND, cu)
		val cdt = if (null == item) ct else ct.append(AND, newCondition("item", item))

		this.findByCondition(cdt).toList.toList
	}
}

object AerobicRecordDao { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

class StrengthRecordDao(serverList: java.util.List[MongoServer]) extends BaseMongoDao[StrengthRecord](serverList) {
	val logger = StrengthRecordDao.logger

	def this(server: (String, Int)) = this(new MongoServer(server._1, server._2) :: Nil)

	/**
		* {user:"user2",item:"strength8",logTime:{$gte:1442304625000, $lt:1442304630000}}
		*/
	def findItems(
		user: String, item: String, logTimeFloor: Long, logTimeCeil: Long
	): List[StrengthRecord]= {
		val cu = newCondition("user", user)
		val ct = newCondition("logTime", newCondition(GTE,logTimeFloor).append(
			AND, newCondition(LT, logTimeCeil))).append(AND, cu)
		val cdt = if (null == item) ct else ct.append(AND, newCondition("item", item))

		this.findByCondition(cdt).toList.toList
	}

}

object StrengthRecordDao{ 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
