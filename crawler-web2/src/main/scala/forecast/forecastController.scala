package net.jadedungeon.forecast

import org.slf4j.LoggerFactory

import jadeutils.common.Logging

import jadeutils.web.BasicController
import jadeutils.web.Method._
import jadeutils.web.DispatherServlet.Foward
import jadeutils.web.DispatherServlet.Redirect

import jadecrawler.website.OpenWeatherParser
import jadecrawler.website.OpenWeatherCrawler

object ForecastController extends BasicController with Logging {

	// /api/conkyforecas/Shanghai%2Ccn/GMT%2B8/Asia%2FShanghai/zh_CN/b1b15e88fa797225412429c1c50c122a
	service("/api/conkyforecas/${city}/${timeZone}/${timeLocal}/${local}/${apiKey}") {
		(info) => {
			val city = info.params("city")(0)
			val timeZone = info.params("timeZone")(0)
			val timeLocal = info.params("timeLocal")(0)
			val local = info.params("local")(0)
			val apiKey = info.params("apiKey")(0)
			val data = OpenWeatherCrawler.get16dayByCityName(city, timeZone, apiKey)
			logDebug("{}", data)
			val result = OpenWeatherParser.formatConkyWeather(timeLocal, local, data)
			logDebug("{}", result)
			info.response.setCharacterEncoding ("UTF-8")
			result
		}
	}

}
