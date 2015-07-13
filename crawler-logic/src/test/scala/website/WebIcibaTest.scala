package jadecrawler.website

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties

@RunWith(classOf[JUnitRunner])
class WebIcibaTest extends FunSuite {
	import scala.io.Source

	val logger = WebIcibaTest.logger

	val icibaHtml = Source.fromFile("src/test/examples/iciba.html").mkString

	test("Test-scalatest") {
		val rec = WebIcibaParser.parse(icibaHtml)
		logger.debug(rec.toString)
		assert(rec != null)
	}

}

object WebIcibaTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
