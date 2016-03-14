import java.io._

def createList(dataFileName: String, filename: String, urlPrefix: String) {
	val urls = loadFromXML(filename)

	// val data = (for (l <- lines) yield findGadenInn(urlPrefix, l)).filter(_ != "")
	val data = urls.filter(filterRoomInfo(urlPrefix, _)).filter(filterRoomIndexPage(urlPrefix, _))
	val writer = new java.io.PrintWriter(new java.io.File(dataFileName))
	data.foreach((r) => {writer.write(r); writer.write("\n");})
	writer.close();
}

def filterRoomIndexPage(urlPrefix: String, str: String) = {
	val g = ("""/accommodations/index\.html?""").r.findFirstIn(str)
	"" == g.getOrElse("")
}


def filterRoomInfo(urlPrefix: String, str: String) = {
	val g = (urlPrefix + """[-a-zA-Z0-9]+/[-a-zA-Z0-9]+/accommodations/.*""").r.findFirstIn(str)
	"" != g.getOrElse("")
}


def loadFromXML(fileName: String) = {
	val urlset = scala.xml.XML.loadFile(fileName)
	(List[String]() /: (urlset \ "url")) { 
		(lst, item) => (item \ "loc").text :: lst
	}
}



createList("data-gi.txt", "sitemapurl-gi-00000.xml", "http://hiltongardeninn3.hilton.com/en/hotels/")
createList("data-hi.txt", "sitemapurl-hi-00000.xml", "http://www3.hilton.com/en/hotels/")
createList("data-ch.txt", "sitemapurl-ch-00000.xml", "http://conradhotels3.hilton.com/en/hotels/")
createList("data-dt.txt", "sitemapurl-dt-00000.xml", "http://doubletree3.hilton.com/en/hotels/")
createList("data-es.txt", "sitemapurl-es-00000.xml", "http://embassysuites3.hilton.com/en/hotels/")
createList("data-hp.txt", "sitemapurl-hp-00000.xml", "http://hamptoninn3.hilton.com/en/hotels/")
createList("data-ht.txt", "sitemapurl-ht-00000.xml", "http://home2suites3.hilton.com/en/hotels/")
createList("data-hw.txt", "sitemapurl-hw-00000.xml", "http://homewoodsuites3.hilton.com/en/hotels/")
createList("data-wa.txt", "sitemapurl-wa-00000.xml", "http://waldorfastoria3.hilton.com/en/hotels/")
createList("data-qq.txt", "sitemapurl-qq-00000.xml", "http://curiocollection3.hilton.com/en/hotels/")

// def format(str: String) = {
// 	val g = """-([A-Z]+)([A-Z][A-Z])/accommodations/index\.html?$""".r.findAllIn(str)
// 	val hotelCode = if (g.hasNext) g.group(1) else ""
// 	val groupCode = if (g.hasNext) g.group(2) else ""
// 	hotelCode+groupCode
// }
// 
// def format2(str: String) = {
// 	val g = """(.*)\t(.*)""".r.findAllIn(str)
// 	val a = if (g.hasNext) g.group(1) else ""
// 	val b = if (g.hasNext) g.group(2) else ""
// 	(a, b)
// }
// 
// import scala.io.Source
// 
// val hotels = Source.fromFile("all.txt").getLines
// val recs = (for (h <- hotels) yield format(h)).toList
// 
// val codes = Source.fromFile("codes.txt").getLines
// val cc = (for (h <- codes) yield format2(h)).toList
// val data = cc.filter((c:(String, String)) => !recs.contains(c._1)).toList
// 
// val writer = new java.io.PrintWriter(new java.io.File("miss.txt"))
// data.foreach((r) => {writer.write(r._1); writer.write("\t"); writer.write(r._2); writer.write("\n");})
// writer.close();
// 
// 
