package jadecrawler.website

import scala.language.postfixOps

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.apache.commons.lang.StringUtils.isNotBlank

import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import jadecrawler.dto.website.IcibaDto
import jadecrawler.dto.website.IcibaS2Dto
import jadecrawler.dto.website.IcibaS3Dto
import jadecrawler.dto.website.IcibaHomoDto
import jadecrawler.website.JsoupUtils._

import scala.collection.JavaConversions._

object WebElongParser {
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	val inDatePatten = """WebCategory: "([a-zA-Z])",""".r

	def parsePage(htmlStr: String): String = {
		val doc = Jsoup parse htmlStr
		val srcBlks= doc select "script"
		val srclist = (for (src <- srcBlks; rec = src.html if rec.contains("""var DetailController =""")) 
					yield { rec }) toList;
		val s = if (null != srclist && srclist.size > 0) { srclist(0) } else {null}
		// logger.debug("DetailController: {}",s)

		val searchId = if(null!=s) {
			var ctx = org.mozilla.javascript.Context.enter();
			var jsBeautyScope = ctx.initStandardObjects();
			var inDate = ctx.evaluateString(jsBeautyScope, s+ " ;\"\"+DetailController.inDate", null, 0, null);
			var outDate = ctx.evaluateString(jsBeautyScope, s+ " ;\"\"+DetailController.outDate", null, 0, null);
			var spuIds= ctx.evaluateString(jsBeautyScope, s+ " ;\"\"+DetailController.AjaxSupplierIDList", null, 0, null);
			var schId= ctx.evaluateString(jsBeautyScope, s+ " ;\"\"+DetailController.DetailSearchId", null, 0, null);
			logger.debug("inDate: {}",inDate)
			logger.debug("outDate: {}",outDate)
			logger.debug("spuIds: {}", spuIds )
			logger.debug("schId: {}", schId)
			spuIds.toString
		} else {null}

		searchId
	}

}

object ElongCrawler {

	lazy val logger = LoggerFactory.getLogger(this.getClass)

	val site = "ihotel.elong.com"

	def process(cityId: String, cityName: String, hotelId: String, 
		inDate: String, outDate: String): String = 
	{
		val page = fetchPage(cityId, cityName, hotelId, inDate, outDate)
		
		val searchId = if (null != page) { WebElongParser.parsePage(page) } 
			else { null }

		val data = if (null != searchId) {
			fetchData(cityId, cityName, hotelId, inDate, outDate, searchId)
		} else { null }
		""
	}


	def fetchData(cityId: String, cityName: String, hotelId: String, 
		inDate: String, outDate: String, searchId: String): String = 
	{
		val pageUrl = "http://ihotel.elong.com/isajax/Detail/getSupplierRoomList/"
		val cookie = genCookie(inDate, outDate)
		val data = try {
			import jadecrawler.net.HTTPUtil
			val resp = HTTPUtil.doPost(pageUrl, HTTPUtil.firefoxParams + 
				// ("Host" -> site) + 
				("Cookie" -> cookie), 
				("hotelid", hotelId):: ("searchid", searchId):: ("regionid", cityId):: 
				("cityen", cityName) :: ("viewpath", "~/views/channel/Detail.aspx") :: 
				("supplierIDList[0]", "2") :: Nil)
			if (null != resp && null != resp.content && resp.content.length > 0) {
				new String(resp.content) 
			} else ""
		} catch { case e: Throwable => {
			logger warn ("elong crawler error: {}, {}, {}, {}, {}, {}", 
				Array(cityId, cityName, hotelId, inDate, outDate, e)); null }
		}
		logger.debug(data)
		data
	}

	def fetchPage(cityId: String, cityName: String, hotelId: String, 
		inDate: String, outDate: String): String = 
	{
		//Thread.sleep(10000)
		val pageUrl = "http://ihotel.elong.com/detail-%s-%s/%s/".format(
			cityName, cityId, hotelId)
		logger debug ("pageurl is: {}", pageUrl)
		val cookie = genCookie(inDate, outDate)
		logger debug ("cookie is: {}", cookie)
		val page = try {
			import jadecrawler.net.HTTPUtil
			val resp = HTTPUtil.doGet(pageUrl, HTTPUtil.firefoxParams + 
				//	("Host" -> site) + 
				("Cookie" -> cookie))
			if (null != resp && null != resp.content && resp.content.length > 0) {
				new String(resp.content) 
			} else ""
		} catch { case e: Throwable => {
			logger warn ("elong crawler error: {}, {}, {}, {}, {}, {}", 
				Array(cityId, cityName, hotelId, inDate, outDate, e)); null }
		}
		// logger.debug(page)
		page
	}

	def genCookie(inDate: String, outDate: String): String =
		"IHotelSearch=OutDate=" + 
			outDate + "+0:00:00&InDate=" + inDate + "+0:00:00&RoomPerson=1|2;"
//		"IHotelSearch=InDate=" + inDate + "&OutDate=" + 
//			outDate + "&RoomPerson=1|2;"
}

