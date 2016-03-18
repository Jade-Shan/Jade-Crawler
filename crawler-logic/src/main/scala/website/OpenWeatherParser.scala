// http://openweathermap.org/API
// http://api.openweathermap.org/data/2.5/forecast?q=Shanghai,cn&mode=xml&appid=b1b15e88fa797225412429c1c50c122a
// http://api.openweathermap.org/data/2.5/forecast/daily?q=Shanghai,cn&mode=xml&units=metric&cnt=7&appid=b1b15e88fa797225412429c1c50c122a

package jadecrawler.website

import scala.language.postfixOps
import jadeutils.common.Logging

import scala.xml.XML

import org.apache.commons.lang.StringUtils.isNotBlank


object OpenWeatherParser extends Logging {

	val str = """<weatherdata><location><name>Shanghai</name><type></type><country>CN</country><timezone></timezone><location altitude="0" latitude="31.222219" longitude="121.458061" geobase="geonames" geobaseid="1796236"></location></location><credit></credit><meta><lastupdate></lastupdate><calctime>0.0146</calctime><nextupdate></nextupdate></meta><sun rise="2016-03-17T22:00:21" set="2016-03-18T10:04:23"></sun><forecast><time day="2016-03-18"><symbol number="500" name="light rain" var="10d"></symbol><precipitation value="1.46" type="rain"></precipitation><windDirection deg="331" code="NNW" name="North-northwest"></windDirection><windSpeed mps="2.01" name="Light breeze"></windSpeed><temperature day="18.36" min="11.28" max="19.4" night="11.28" eve="19.08" morn="17"></temperature><pressure unit="hPa" value="1026.48"></pressure><humidity value="100" unit="%"></humidity><clouds value="overcast clouds" all="92" unit="%"></clouds></time><time day="2016-03-19"><symbol number="500" name="light rain" var="10d"></symbol><precipitation value="0.23" type="rain"></precipitation><windDirection deg="47" code="NE" name="NorthEast"></windDirection><windSpeed mps="3.77" name="Gentle Breeze"></windSpeed><temperature day="15.02" min="6.89" max="18.86" night="6.89" eve="17.12" morn="10.03"></temperature><pressure unit="hPa" value="1032.9"></pressure><humidity value="79" unit="%"></humidity><clouds value="clear sky" all="8" unit="%"></clouds></time><time day="2016-03-20"><symbol number="800" name="clear sky" var="02d"></symbol><precipitation></precipitation><windDirection deg="65" code="ENE" name="East-northeast"></windDirection><windSpeed mps="4.71" name="Gentle Breeze"></windSpeed><temperature day="15.29" min="3.6" max="17.08" night="7.55" eve="14.23" morn="3.6"></temperature><pressure unit="hPa" value="1036.45"></pressure><humidity value="59" unit="%"></humidity><clouds value="clear sky" all="8" unit="%"></clouds></time><time day="2016-03-21"><symbol number="502" name="heavy intensity rain" var="10d"></symbol><precipitation value="15.16" type="rain"></precipitation><windDirection deg="115" code="ESE" name="East-southeast"></windDirection><windSpeed mps="6.62" name="Moderate breeze"></windSpeed><temperature day="12.79" min="11.07" max="12.79" night="12.12" eve="11.65" morn="11.07"></temperature><pressure unit="hPa" value="1030.19"></pressure><humidity value="0" unit="%"></humidity><clouds value="overcast clouds" all="100" unit="%"></clouds></time><time day="2016-03-22"><symbol number="501" name="moderate rain" var="10d"></symbol><precipitation value="11.99" type="rain"></precipitation><windDirection deg="84" code="E" name="East"></windDirection><windSpeed mps="6.84" name="Moderate breeze"></windSpeed><temperature day="15.99" min="11.54" max="15.99" night="11.54" eve="12.55" morn="13.37"></temperature><pressure unit="hPa" value="1023.35"></pressure><humidity value="0" unit="%"></humidity><clouds value="overcast clouds" all="97" unit="%"></clouds></time><time day="2016-03-23"><symbol number="501" name="moderate rain" var="10d"></symbol><precipitation value="10.75" type="rain"></precipitation><windDirection deg="18" code="NNE" name="North-northeast"></windDirection><windSpeed mps="5.51" name="Moderate breeze"></windSpeed><temperature day="11.39" min="8.22" max="11.39" night="8.22" eve="9.62" morn="10.57"></temperature><pressure unit="hPa" value="1029.89"></pressure><humidity value="0" unit="%"></humidity><clouds value="overcast clouds" all="97" unit="%"></clouds></time><time day="2016-03-24"><symbol number="500" name="light rain" var="10d"></symbol><precipitation value="1.67" type="rain"></precipitation><windDirection deg="25" code="NNE" name="North-northeast"></windDirection><windSpeed mps="7.47" name="Moderate breeze"></windSpeed><temperature day="13.35" min="6.82" max="13.35" night="6.82" eve="7.55" morn="8.62"></temperature><pressure unit="hPa" value="1036.05"></pressure><humidity value="0" unit="%"></humidity><clouds value="few clouds" all="24" unit="%"></clouds></time></forecast></weatherdata>"""

	val iconMap = Map( "01d" ->"a", "01" ->"N", "02d" ->"b", "02" ->"l",
		"03d" ->"d", "03" ->"n", "04d" ->"e", "04" ->"o", "09d" ->"h", "09" ->"r",
		"10d" ->"g", "10" ->"q", "11d" ->"i", "11" ->"s")

	def parse16day(str: String, localTimeZone: String) = {
		val data = scala.xml.XML.loadString(str)
		val location = (data \ "location" \ "name").text
		val country = (data \ "location" \ "country").text
		val sunRise= parseTime((data \ "sun" \ "@rise").text.replaceAll("T", ""), 
			localTimeZone)
		val sunSet = parseTime((data \ "sun" \ "@set").text.replaceAll("T", ""), 
			localTimeZone)

		val days = (List[Map[String, String]]() /: (data \\ "time")) {
			(lst, rec) =>
			val day = (rec \ "@day").toString                   // 日期
			val smbNum  = (rec \ "symbol" \ "@number").toString  // 天气符号
			val smbVar  = (rec \ "symbol" \ "@var").toString  // 天气符号
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
			Map("day" -> day, "symbol" -> smbVar, "smbNum" -> smbNum, 
				"pcpType" -> pcpType, "pcpValue" -> pcpValue, 
				"humUnt" -> humUnt, "humVal" -> humVal,
				"wndDct" -> wndDct, "wndSpd" -> wndSpd, "wndMps" -> wndMps ,
				"tmpMin" -> tmpMin, "tmpMax" -> tmpMax, "tmpMrn" -> tmpMrn, 
				"tmpDay" -> tmpDay, "tmpNig" -> tmpNig, "tmpEve" -> tmpEve, 
				"psuUnt" -> psuUnt ,"psuVal" -> psuVal, 
				"cludVal" -> cludVal, "cludAll" -> cludAll, "cludUnt" -> cludUnt) :: lst
		}.sortWith((a, b) => a.getOrElse("day", "").compareTo(
			b.getOrElse("day", "")) < 0)

		(location + ", " + country, sunRise, sunSet, days)
	}

	private[this] def parseTime(time: String, localTimeZone: String) = {
		import java.util.Date;
		import java.util.TimeZone;

		def transTimezone(date: Date, src: String, dest: String) = new Date(
			date.getTime() - TimeZone.getTimeZone(src).getRawOffset() + 
			TimeZone.getTimeZone(dest).getRawOffset())

		val sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		val date = new Date(sdf.parse("2016-03-17 22:00:02").getTime())

		val sdf2 = new java.text.SimpleDateFormat("HH:mm")
		sdf2.format(transTimezone(date, "GMT", localTimeZone))
	}


	// println("")


}

