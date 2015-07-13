package jadecrawler.net

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class ClientIntegrationTest extends FunSuite {

	test("Test-HTTP-Get") {
		// val response = HTTPUtil.doGet("http://www.yyets.com/eresourcelist?page=1&channel=&area=&category=&format=&year=&sort=", HTTPUtil.firefoxParams + ("Host" -> "www.yyets.com"))
		// val response = HTTPUtil.doGet("http://www.yyets.com/eresourcelist?page=2&channel=&area=&category=&format=&year=&sort=", HTTPUtil.firefoxParams + ("Host" -> "www.yyets.com"))
		// val response = HTTPUtil.doGet("http://www.yyets.com/resource/30010", HTTPUtil.firefoxParams + ("Host" -> "www.yyets.com"))
		val response = HTTPUtil.doGet("http://www.iciba.com/intellectual", HTTPUtil.firefoxParams + ("Host" -> "www.iciba.com"))
		ClientIntegrationTest.logger.debug(response.toString)
		ClientIntegrationTest.logger.debug(new String(response.content))
	}

	test("Test-HTTP-Post") {
		val response = HTTPUtil.doPost("http://www.iciba.com/intellectual", HTTPUtil.firefoxParams + ("Host" -> "www.iciba.com"), ("aaa", "bbb") :: Nil)
		// val response = HTTPUtil.doPost("http://localhost:8080/jadeutils-cdn/test-session.jsp", HTTPUtil.firefoxParams, ("aaa", "bbb") :: Nil)
		ClientIntegrationTest.logger.debug(response.toString)
		ClientIntegrationTest.logger.debug(new String(response.content))
	}

}

object ClientIntegrationTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
