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

}

