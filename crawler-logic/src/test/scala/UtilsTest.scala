package jadecrawler.website

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties


@RunWith(classOf[JUnitRunner])
class UtilsTest extends FunSuite {
	lazy val logger = UtilsTest.logger

	test("test-format-js") {
		val hu = new HttpBeautifyUtils
		val res = hu.formatJs("""if (a >0) { "<a href=\"http://www.google.com\">google</a>" } else { "<input type=\"text\" />" }""")
		assert(
		"""|if (a > 0) {
			|    "<a href=\"http://www.google.com\">google</a>"
			|} else {
			|    "<input type=\"text\" />"
			|}""".stripMargin
			== res.toString)
	}

}

object UtilsTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
