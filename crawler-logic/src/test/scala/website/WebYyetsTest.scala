package jadecrawler.website

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties


@RunWith(classOf[JUnitRunner])
class WebYyetsTest extends FunSuite {
	import scala.io.Source


	val logger = WebYyetsTest.logger

	val yyetsListHtml = Source.fromFile("src/test/examples/yyets-list.html").mkString
	val yyetsinfo01tHtml = Source.fromFile("src/test/examples/yyets-info.html").mkString
	val yyetsinfo02tHtml = Source.fromFile("src/test/examples/yyets-info.02.html").mkString

	test("Test-YYETS-list") {
//		val recs = YyetsRecListParser.parse(yyetsListHtml)
//		logger debug ("{}", recs.size)
//		logger debug ("{}", recs.toString)
//		assert(recs.size == 20)
	}

	test("Test-YYETS-info") {
//		val recs1 = YyetsRecInfoParser.parse(yyetsinfo01tHtml,"0000000000000001", "aaaaaaaaaa")
//		logger debug ("{}", recs1.size)
//		logger debug ("{}", recs1.toString)
//		assert(recs1.size == 385)
//
//		val recs2 = YyetsRecInfoParser.parse(yyetsinfo02tHtml, "0000000000000002", "bbbbbbbbbb")
//		logger debug ("{}", recs2.size)
//		logger debug ("{}", recs2.toString)
//		assert(recs2.size == 385)
	}

}

object WebYyetsTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}



@RunWith(classOf[JUnitRunner])
class WebYyetsIntegrationTest extends FunSuite {

	val logger = WebYyetsIntegrationTest.logger


	test("Test-YYETS-item") {
	}


}

object WebYyetsIntegrationTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
