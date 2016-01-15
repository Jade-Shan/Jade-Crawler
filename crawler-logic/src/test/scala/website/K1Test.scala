package jadecrawler.website

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements


import java.util.Properties
import jadeutils.common.Logging

@RunWith(classOf[JUnitRunner])
class K1Test extends FunSuite with Logging{
	import scala.io.Source

	test("Test-scalatest") {
		// val bookStr = K1Crawler.fetchBook("manhua8354")
		// val book = K1Parser.parseBook("manhua8354", bookStr)
		// logInfo("{}", book)

		// val volDatas = for (vol <- book._3) yield {
		// 	val volData = K1Crawler.fetchVol(vol._1)
		// 	val cid = if(null == volData) { "no page"} else {
		// 		val g = """.+ +var +cid *= *(\d+) *;?""".r.findAllIn(volData)
		// 		if (g.hasNext) g.group(1) else "no find"
		// 	}
		// 	val pages = K1Parser.parseVol(vol._1, vol._2, cid, vol._3)
		// 	val ll = for (p <- pages) yield K1Crawler.fetchPage(p._1, p._3)
		// 	val urls = for (l <- ll) yield K1Parser.parseImage(l)
		// 	for(i <- 0 until urls.size) {
		// 		val url = urls(i)
		// 		val g = """http://manhua.+\.cdndm5.com.+(\.[0-9a-zA-Z])\?cid=.+""".r.findAllIn(url)
		// 		val postfix = if (g.hasNext) g.group(1) else ".jpg"
		// 		K1Crawler.saveImage(vol._1 + "-" + "%03d".format(i + 1) + postfix, K1Crawler.loadImage(url, vol._1))
		// 	}
		// 	urls
		// }

		// K1Crawler.processBook("manhua6427")

		// K1Crawler.processVol("manhua6427", "至黑之夜", ("ch6-71691", "第6话", 33))
		K1Crawler.processVol("manhua6427", "至黑之夜", ("ch7-73379", "第7话", 26))
		K1Crawler.processVol("manhua6427", "至黑之夜", ("sp4-75022", "外传：第4话", 28))
		K1Crawler.processVol("manhua6427", "至黑之夜", ("sp5-76719", "外传：第5话", 26))
		K1Crawler.processVol("manhua6427", "至黑之夜", ("ch8-86053", "第8话", 35))

// import java.io.File
// import sys.process.Process
// val p = Process("""bash ./aa.sh aaa bbb""", new File("books"), ("LANG", "en_US"))
// p!

	}

}

object K1Test { 
}

