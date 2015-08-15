package jadeutils.web

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.net.URI
import java.util.Properties

@RunWith(classOf[JUnitRunner])
class UrlTest extends FunSuite {

	test("Test-Url-Prefix") {
		ExampleTest.logger.debug("test scalatest")
		val uri = new URI("http://example.com/a%2Fb%3Fc/aaa/bbb/ccc/ddd");

		uri.getRawPath().split("/").foreach(System.err.println(_))
		assert(2 > 1)
	}

}

object ExampleTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
