package jadecrawler.web2

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties

@RunWith(classOf[JUnitRunner])
class AerobicRecordRecordDaoTest extends FunSuite {
	val logger = AerobicRecordRecordDaoTest.logger

	val recs = for (i <- 0 until 10) yield { 
		new AerobicRecord("user" + i, "item" + i, i, i, i)
	}

	test("Test-AeroRec-toString") {
		recs.foreach(logger info _.toString)
	}

}

object AerobicRecordRecordDaoTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

@RunWith(classOf[JUnitRunner])
class StrengthRecordDaoTest extends FunSuite {
	val logger = StrengthRecordDaoTest.logger

	val recs = for (i <- 0 until 10) yield { 
		new StrengthRecord("user" + i, "item" + i, i, i)
	}

	test("Test-StnRec-toString") {
		recs.foreach(logger info _.toString)
	}

}

object StrengthRecordDaoTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

