package jadecrawler.web2

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties

import jadeutils.mongo.MongoServer


@RunWith(classOf[JUnitRunner])
class AerobicRecordRecordDaoIntegrationTest extends FunSuite {
	import scala.collection.JavaConversions._

	val logger = AerobicRecordRecordDaoTest.logger

	val dao = new AerobicRecordDao(new MongoServer("mongo.local-vm", 27017) :: Nil)

	val recs = for (i <- 0 until 10) yield { 
		new AerobicRecord("user" + i, "aerobic" + i, i, i, i)
	}

	test("Test-AeroRec-toString") {
		recs.foreach(logger info _.toString)
	}

	test("Test-AeroRec-Save") {
		recs.foreach(dao insert _)
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

	val dao = new StrengthRecordDao(new MongoServer("mongo.local-vm", 27017) :: Nil)

	val recs = for (i <- 0 until 10) yield { 
		new StrengthRecord("user" + i, "strength" + i, i, i)
	}

	test("Test-StnRec-toString") {
		recs.foreach(logger info _.toString)
	}

	test("Test-StnRec-Save") {
		recs.foreach(dao insert _)
	}

}

object StrengthRecordDaoTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

