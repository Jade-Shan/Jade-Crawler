package jadecrawler.website

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties

@RunWith(classOf[JUnitRunner])
class WebElongTest extends FunSuite {
	import scala.io.Source

	val logger = WebElongTest.logger

	test("Test-scalatest") {
//		ElongCrawler.process("179893","florence_province_","103226","2015-01-27", "2015-01-28")
//		ElongCrawler.process("179893","florence_province_","103226","2015-01-28", "2015-01-29")
//		ElongCrawler.process("179893","florence_province_","103226","2015-01-29", "2015-01-30")
//		ElongCrawler.process("179893","florence_province_","103226","2015-01-30", "2015-01-31")
//		ElongCrawler.process("179893","florence_province_","103226","2015-01-31", "2015-02-01")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-01", "2015-02-02")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-02", "2015-02-03")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-03", "2015-02-04")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-04", "2015-02-05")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-05", "2015-02-06")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-06", "2015-02-07")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-07", "2015-02-08")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-08", "2015-02-09")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-09", "2015-02-10")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-10", "2015-02-11")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-11", "2015-02-12")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-12", "2015-02-13")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-13", "2015-02-14")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-14", "2015-02-15")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-15", "2015-02-16")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-16", "2015-02-17")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-17", "2015-02-18")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-18", "2015-02-19")
//		ElongCrawler.process("179893","florence_province_","103226","2015-02-19", "2015-02-20")
	}

}

object WebElongTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
