package jadecrawler.website

import java.io.File
import java.io.FileOutputStream


import scala.language.postfixOps
import sys.process.Process

import jadeutils.common.Logging

import org.apache.commons.lang.StringUtils.isNotBlank

import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import jadeutils.common.JsoupUtils._

import scala.collection.JavaConversions._

object K1Parser extends Logging {

	/*
	 * ("编号001", "书名001", 
		 * ("113513", "第0卷", 32) :: ("93765", "第1卷", 24) :: Nil)
	 *
	 */
	def parseBook(bookId: String, bookPage: String) = {
		val doc = Jsoup parse bookPage
		val title = (doc select "div.sy_k21>h1").text + "-" + (doc select "div.sy_k21>h2").text
		logger debug ("title:{}", title)
		val lines = doc select "div#cp_a1>ul.sy_nr1:nth-child(8)>li"
		val vols = for (line <- lines; item = genVolFromBook(line) if item != None) yield item
		(bookId, title, vols)
	}

	def genVolFromBook(line: Element): (String, String, Int) = {
		val volId = line.select("a").attr("href").replaceAll("/","")
		val recStr = line.text
		val g = """(.*) *（(\d+)页）""".r.findAllIn(recStr)
		if (g.hasNext)
			(volId, g.group(1), Integer.parseInt(g.group(2))) else null
	}

	def parseCid(volData: String) = {
		if(null == volData) { "no page"} else {
			val g = """.+ +var +cid *= *(\d+) *;?""".r.findAllIn(volData)
			if (g.hasNext) g.group(1) else "no find"
		}
	}

	def parseVol(volId: String, volName: String, cid: String, pageCount: Int) = 
	for(i <- 1 to pageCount) yield {
		val url = "http://%s/%s/chapterfun.ashx?cid=%s&page=%d&key=&maxcount=10"
		(volId, i, url.format(K1Crawler.site, volId, cid, i))
	}

	def parseImage(scripts: String) = {
		import jadeutils.common.JavascriptUtils
		JavascriptUtils.evaluateString(scripts + "; d[0];")._2
	}

}

object K1Crawler extends Logging {
	val site = "tel.1kkk.com"
	val localVolPath = "books/"

	/* 取得一本书
	 */
	def processBook(bookId: String) {
		val bookPage = fetchBook(bookId)
		val book = K1Parser.parseBook(bookId, bookPage)
		for (vol <- book._3) 
			processVol(book._1, book._2, vol)
	}

	def fetchBook(bookId: String) = {
		val url = "http://" + site +"/" + bookId + "/"
		val refer = url
		try {
			import jadecrawler.net.HTTPUtil
			val resp = HTTPUtil.doGet(url, 
				HTTPUtil.firefoxParams + ("Host" -> site) + ("Referer" -> refer))
			if (null != resp && null != resp.content && resp.content.length > 0) {
				new String(resp.content) 
			} else ""
		} catch { case e: Throwable => {
			logger warn ("fetch 1kk page error: {}, {}", Array(url, e)); null }
		}
	}

	def processVol(bookId: String, bookName: String, vol: (String, String, Int)) {
		// mkdir for val
		val bookDir = new File(localVolPath)
		makeDir(bookDir)
		makeDir(new File(localVolPath + bookId + "/"))
		val volPath = localVolPath + bookId + "/" + vol._1 + "/"
		makeDir(new File(volPath))

		val volData = fetchVol(vol._1)
		val cid = K1Parser.parseCid(volData)
		val pages = K1Parser.parseVol(vol._1, vol._2, cid, vol._3)
		val ll = for (p <- pages) yield K1Crawler.fetchPage(p._1, p._3)
		val urls = for (l <- ll) yield K1Parser.parseImage(l)
		logger debug ("{}", urls)
		for(i <- 0 until urls.size) {
			val url = urls(i)
			val g = """http://manhua.+\.cdndm5.com.+(\.[0-9a-zA-Z])\?cid=.+""".r.findAllIn(url)
			val postfix = ".jpg"//if (g.hasNext) g.group(1) else ".jpg"
			saveImage(volPath + "%03d".format(i + 1) + postfix, 
				loadImage(url, vol._1))
		}
		// genPdf
		val pdfFileName = bookName + "-" + vol._2
		val p = Process("""bash ./genPdf.sh %s %s """.format(bookId + "/" + vol._1 + "/", pdfFileName), 
			bookDir, ("LANG", "en_US"))
		p!
	}

	def fetchVol(volId: String) = {
		val url = "http://" + site +"/" + volId + "/"
		val refer = url
		try {
			import jadecrawler.net.HTTPUtil
			val resp = HTTPUtil.doGet(url, 
				HTTPUtil.firefoxParams + ("Host" -> site) + ("Referer" -> refer))
			if (null != resp && null != resp.content && resp.content.length > 0) {
				new String(resp.content) 
			} else ""
		} catch { case e: Throwable => {
			logger warn ("fetch 1kk page error: {}, {}", Array(url, e)); null }
		}
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
			logger warn ("fetch 1kk img info error: {}, {}", Array(url, e)); null }
		}
		page
	}

	def loadImage(url: String, volId: String): Array[Byte] = {
		val g = """http://(manhua([-.0-9a-zA-Z])+.cdndm5.com)""".r.findAllIn(url)
		val site = if (g.hasNext) g.group(1) else "manhua-923-2.cdndm5.com"                     		
		val refer = "http://" + site +"/m" + volId + "/"
		try {
			import jadecrawler.net.HTTPUtil
			val resp = HTTPUtil.doGet(url, 
				HTTPUtil.firefoxParams + ("Host" -> site) + ("Referer" -> refer))
			if (null != resp && null != resp.content && resp.content.length > 0) {
				resp.content 
			} else new Array[Byte](0)
		} catch { 
			case e: Throwable => {
				logger warn ("fetch 1kk image error: {}, {}", Array(url, e)); 
				new Array[Byte](0) 
			}
		}
	}

	def saveImage(fileName: String, content: Array[Byte]) {
		val file = new File(fileName)
		val out = new FileOutputStream(file)
		out.write(content)
		out.close()
	}

	def processPage() {
	}

	def makeDir(dir: File): Unit = if(!dir.exists) dir.mkdir()
}

