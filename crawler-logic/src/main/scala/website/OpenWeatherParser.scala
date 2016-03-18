// http://openweathermap.org/API
// http://api.openweathermap.org/data/2.5/forecast?q=Shanghai,cn&mode=xml&appid=b1b15e88fa797225412429c1c50c122a
// http://api.openweathermap.org/data/2.5/forecast/daily?q=Shanghai,cn&mode=xml&units=metric&cnt=7&appid=b1b15e88fa797225412429c1c50c122a

package jadecrawler.website

import scala.language.postfixOps
import jadeutils.common.Logging

import scala.xml.XML

import org.apache.commons.lang.StringUtils.isNotBlank


object OpenWeatherParser extends Logging {

	def parse16day(str: String) = (
		List[Map[String, String]]() /: scala.xml.XML.loadString(str) \\ "time")
	{ 
		(lst, rec) =>
		val day = (rec \ "@day").toString                   // 日期
		val symbol  = (rec \ "symbol" \ "@number").toString  // 天气符号
		val pcpType = (rec \ "precipitation" \ "@type").toString  // 降水类型
		val pcpValue = (rec \ "precipitation" \ "@value").toString // 降水量
		val humUnt  = (rec \ "humidity" \ "@unit").toString // 降水量概率
		val humVal  = (rec \ "humidity" \ "@value").toString // 降水量概率
		val wndDct  = (rec \ "windDirection" \ "@code").toString // 风向 
		val wndSpd  = (rec \ "windSpeed" \ "@name").toString // 风强度
		val wndMps  = (rec \ "windSpeed" \ "@mps").toString // 风速度
		val tmpMin  = (rec \ "temperature" \ "@min").toString // 温度
		val tmpMax  = (rec \ "temperature" \ "@max").toString // 温度
		val tmpMrn  = (rec \ "temperature" \ "@morn").toString // 温度
		val tmpDay  = (rec \ "temperature" \ "@day").toString // 温度
		val tmpNig  = (rec \ "temperature" \ "@night").toString // 温度
		val tmpEve  = (rec \ "temperature" \ "@eve").toString // 温度
		val psuUnt  = (rec \ "pressure" \ "@unit").toString // 气压
		val psuVal  = (rec \ "pressure" \ "@value").toString // 气压
		val cludVal = (rec \ "clouds" \ "@value").toString // 云层
		val cludAll = (rec \ "clouds" \ "@all").toString // 云层
		val cludUnt = (rec \ "clouds" \ "@unit").toString // 云层
		Map("day" -> day, "symbol" -> symbol, "pcpType" -> pcpType,
			"pcpValue" -> pcpValue, "humUnt" -> humUnt, "humVal" -> humVal,
			"wndDct" -> wndDct, "wndSpd" -> wndSpd, "wndMps" -> wndMps ,
			"tmpMin" -> tmpMin, "tmpMax" -> tmpMax, "tmpMrn" -> tmpMrn, 
			"tmpDay" -> tmpDay, "tmpNig" -> tmpNig, "tmpEve" -> tmpEve, 
			"psuUnt" -> psuUnt ,"psuVal" -> psuVal, 
			"cludVal" -> cludVal, "cludAll" -> cludAll, "cludUnt" -> cludUnt) :: lst
	}.sortWith((a, b) => ( a.getOrElse("day", "") compareTo b.getOrElse("day", "")) < 0)



}

