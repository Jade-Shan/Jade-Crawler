package jadecrawler.website

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import jadeutils.common.Logging

import java.util.Properties

@RunWith(classOf[JUnitRunner])
class OpenWeatherTest extends FunSuite with Logging {

	test("Test-16days") {
		val result = OpenWeatherCrawler.get16dayByCityName("Shanghai,cn", "GMT+8", "b1b15e88fa797225412429c1c50c122a")
		logDebug("{}", result)
		val str = OpenWeatherParser.formatConkyWeather("Asia/Shanghai", "zh_CN", result)
		logDebug("\n{}", str)
	}
}
