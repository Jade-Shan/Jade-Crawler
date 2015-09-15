package jadecrawler.web2

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties

@RunWith(classOf[JUnitRunner])
class AerobicRecordRecordDaoIntegrationTest extends FunSuite {
	import scala.collection.JavaConversions._

	val logger = AerobicRecordRecordDaoTest.logger

	val dao = new AerobicRecordDao("mongo.local-vm", 27017)

	val recs = for (i <- 0 until 10) yield { 
		new AerobicRecord("user" + (if (i < 5) 0 else 2), "aerobic" + i, i, i, i, 
			System.currentTimeMillis)
	}

	test("Test-AeroRec-toString") {
		recs.foreach(logger info _.toString)
	}

	test("Test-AeroRec-Save") {
		recs.foreach(dao insert _)
	}

	test("Test-AeroRec-find") {
		val ll = dao.findItems("user2", "aerobic9", 1442304500000L, 1442308000000L)
		assert(ll.size > 0)
		ll.foreach(logger info _.toString)
	}

}

object AerobicRecordRecordDaoTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

@RunWith(classOf[JUnitRunner])
class StrengthRecordDaoIntegrationTest extends FunSuite {
	import scala.collection.JavaConversions._

	val logger = StrengthRecordDaoTest.logger

	val dao = new StrengthRecordDao("mongo.local-vm", 27017)

	val recs = for (i <- 0 until 10) yield { 
		new StrengthRecord("user" + (if (i < 5) 0 else 2), "strength" + i, i, i, 
			System.currentTimeMillis)
	}

	test("Test-StnRec-toString") {
		recs.foreach(logger info _.toString)
	}

	test("Test-StnRec-Save") {
		recs.foreach(dao insert _)
	}

	test("Test-StnRec-find") {
		val ll = dao.findItems("user2", "strength8", 1442304500000L, 1442308000000L)
		assert(ll.size > 0)
		ll.foreach(logger info _.toString)
	}

}

object StrengthRecordDaoTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

