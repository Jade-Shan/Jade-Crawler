package jadecrawler.website

import scala.language.postfixOps

import jadeutils.common.Logging

import org.apache.commons.lang.StringUtils.isNotBlank

import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import jadeutils.common.JsoupUtils._

import scala.collection.JavaConversions._

object Dm5Parser extends Logging {

	/*
	 * ("编号001", "书名001", 
		 * ("113513", "第0卷", 32) :: ("93765", "第1卷", 24) :: Nil)
	 *
	 */
	def parseBook(bookPage: String) = {
		val vols = ("113513", "第0话", 32) :: ("93765", "第1话", 24) :: Nil
		("编号001", "书名001", vols)
	}

	def parseVol(volId: String, volName: String, pageCount: Int) = 
	for(i <- 1 to pageCount) yield {
		(volId, i, "http://%s/m%s/chapterfun.ashx?cid=%s&page=%d&key=&language=1&gtk=6".format(
			Dm5Crawler.site, volId, volId, i).toString)
	}

	def parseImage(scripts: String) = {
		import jadeutils.common.JavascriptUtils
		JavascriptUtils.evaluateString(scripts + "; d[0];")._2
	}

}

object Dm5Crawler extends Logging {
	val site = "www.dm5.com"
	val localVolPath = "books/"

	/* 取得一本书
	 */
	def processBook(bookId: String) {
		val bookPage = ""
		val book = Dm5Parser.parseBook(bookPage)
		for (vol <- book._3) processVol(book._1, book._2, vol)
		}

	def processVol(bookid: String, bookName: String, vol: (String, String, Int)) {
		// TODO: mkdir for val
		val volPath = localVolPath + bookid + "/" + vol._1 + "/"

		val pages = Dm5Parser.parseVol(vol._1, vol._2, vol._3)
		val ll = for (p <- pages) yield Dm5Crawler.fetchPage(p._1, p._3)
		val urls = for (l <- ll) yield Dm5Parser.parseImage(l)
		logger debug ("{}", urls)
	}

	def fetchPage(volId: String, url: String) = {
		val refer = "http://" + site +"/m" + volId + "/"
		val page = try {
			import jadecrawler.net.HTTPUtil
			val resp = HTTPUtil.doGet(url, 
				HTTPUtil.firefoxParams + ("Host" -> site) + ("Referer" -> refer))
			if (null != resp && null != resp.content && resp.content.length > 0) {
				new String(resp.content) 
			} else ""
		} catch { case e: Throwable => {
			logger warn ("fetch dm5 page error: {}, {}", Array(url, e)); null }
		}
		page
	}

	def processPage() {
	}

	
}
