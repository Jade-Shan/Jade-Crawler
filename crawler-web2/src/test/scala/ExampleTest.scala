package example

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties

import jadeutils.common.Logging

@RunWith(classOf[JUnitRunner])
class ExampleTest extends FunSuite with Logging {

	test("Test-scalatest") {
		logger.debug("test scalatest")
		assert(2 > 1)
	}

	test("Test-call-java") {
		logger.debug("test call java")
		logger.debug(JavaExample.callJava("Call Java OK"))
		assert("Call Java OK" == JavaExample.callJava("Call Java OK"))
	}

	test("Test-load-prop") {
		val prop: Properties = new Properties()
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("example.properties"))
		val server = prop.getProperty("conn.server")
		logger.debug(server)
		// assert("127.0.0.1" == server)
	}

}
