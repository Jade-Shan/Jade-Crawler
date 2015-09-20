package jadecrawler.website

import scala.language.postfixOps

import jadeutils.common.Logging

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

import jadeutils.common.JsoupUtils._

import scala.collection.JavaConversions._

object WebIcibaParser extends Logging {

	def parse(htmlStr: String): Option[IcibaDto] = {
		import scala.collection.JavaConversions._

		val doc = Jsoup parse htmlStr
		val word = (doc select "html>body>div#layout>div#center>div#main_box" + 
			">div#dict_main>div.dictbar>div.dict_title>div.title>span" + 
			">h1#word_name_h1").text
		logger trace word

		val prons = doc select "html>body>div#layout>div#center>div#main_box" + 
			">div#dict_main>div.dictbar>div.dict_title>div.prons>span"
		val pronuctions = (for (p <- prons; rec = genPron(p) if rec != None) 
					yield { rec.getOrElse(null) }) toList;
		logger trace pronuctions.toString
		
		val exps = doc select "html>body>div#layout>div#center>div#main_box" + 
			">div#dict_main>div.dictbar>div.group_prons>div.group_pos>p"
		val explations = (for (e <- exps; rec = genExps(e)) yield rec) toList;
		logger trace explations.toString
		
		val rlts = doc select "html>body>div#layout>div#center>div#main_box" + 
			">div#dict_main>div.dictbar>div.group_prons>div.group_inf>ul>li"
		val relatedWords = (for (r <- rlts; rec = genRelated(r) if null != rec) 
			yield rec) toList;
		logger trace relatedWords.toString

		val examps = doc select "div#dict_content_104>dl.vDef_list"
		val examples = (for (r <- examps; rec = genExamps(r)) yield rec) toList;
		logger trace examples.toString

		val homos = doc select "div#dict_content_5>dl.def_list>dd"
		val homoionyms = (for (r <- homos; rec = genHomo(r) if null != rec) 
			yield rec) toList;
		logger trace homoionyms.toString

		Some(new IcibaDto(word, pronuctions, explations, relatedWords, examples,
			homoionyms))
	}

	val pronPattern = """^(\S+)\s?\[(.+)\]$""".r
	def genPron(e: Element) = (e select "span>span").text match {
		case pronPattern(a, b) => Some(new IcibaS3Dto(a, b, ""))
		case _ => None
	}

	def genExps(e: Element) = new IcibaS2Dto((e select "p>strong.fl").text, 
		(e select "p>span").text)

	def genRelated(e: Element) = {
		val wordType = e.ownText.replace("：","")
		val arr = (e select "li>a")
		val word = if (arr.size > 0) arr(0).ownText else "";

		if (isNotBlank(wordType) && isNotBlank(word) && 
			!(wordType contains "大家都在背")) new IcibaS2Dto(wordType, word)
		else
			null
	}

	def genExamps(e: Element) = {
		val chs = (e select "dl>dd").text
		val all = e.text.replaceAll((e select "dl>p").text, "").replaceAll(chs, "")
		new IcibaS3Dto(all.replaceAll("""^\s*\d+\s*\.\s*""", ""), chs, "")
	}

	def genHomo(e: Element) = {
		import scala.collection.JavaConversions._

		val mean = (e select "dd>div>span.text_blue").text
		val words = e select "dd>div.ct_example>ul>li"
		val recs: java.util.List[IcibaS2Dto] = (for (w <- words; c = w.ownText.replaceAll("：",""); 
				e = (w select "li>a").text if isNotBlank(c) && isNotBlank(e)) 
			yield new IcibaS2Dto(e, c)).toList
		if (isNotBlank(mean)) new IcibaHomoDto(mean, recs) else null
	}

}

object IcibaCrawler extends Logging {
	import scala.collection.JavaConversions._

	val site = "www.iciba.com"

	def process(word: String) = {
		val page = try {
			import jadecrawler.net.HTTPUtil
			val resp = HTTPUtil.doGet(
				"http://" + site + "/%s" format word.replaceAll(" ", "_"),
				HTTPUtil.firefoxParams + ("Host" -> site))
			if (null != resp && null != resp.content && resp.content.length > 0) {
				new String(resp.content) 
			} else ""
		} catch { case e: Throwable => {
			logger warn ("query iciba error: {}, {}", Array(word, e)); null } }

		if (null != page) {
			try { WebIcibaParser.parse(page).getOrElse(null) }
			catch { case e: Throwable => {
				logger warn ("iciba page parse err: {}, {}", Array(word, e)); null}}}
		else { null }
	}

	import jadeutils.mongo.MongoServer
	import jadeutils.mongo.BaseMongoDao
	import jadeutils.mongo.Condition.newCondition

	import jadecrawler.dto.website.NewWordBook
	import jadecrawler.dto.website.NewWord

	class IcibaDao(serverList: java.util.List[MongoServer]) 
		extends BaseMongoDao[IcibaDto](serverList)

	class NewWordBookDao(serverList: java.util.List[MongoServer]) 
		extends BaseMongoDao[NewWordBook](serverList)

	def getDao(host: String, port: Int) = 
		new IcibaDao(new MongoServer(host, port) :: Nil)


	def saveLocal(dao: IcibaDao, result: IcibaDto) {
		if (null != result) {
			logger info ("iciba page parse OK: {}", result.word)
			try { 
				dao insert result 
				logger info ("iciba page save OK: {}", result.word)
			} catch {
				case e: Throwable => logger error ("iciba-save error: {}, {}", 
					Array(result.word, e))
			}
		}
	}

	def loadLocal(dao: IcibaDao, word: String) = try {
		val rs = dao.findByCondition(newCondition("word", word))
		if (rs.hasNext) rs.next else null
	} catch { case e: Throwable => {
		logger warn ("iciba load local err: {}, {}", Array(word, e)); null}
	}

	def loadNewWords(host: String, port: Int, username: String, 
		password: String): java.util.List[NewWord] =
	{
		val dao = new NewWordBookDao(new MongoServer(host, port) :: Nil)
		val user = try {
			val rs = dao.findByCondition(newCondition("username", username))
			if (rs.hasNext) rs.next else null
		} catch { case e: Throwable => {
			logger warn ("find iciba user eror: {}, {}", Array(username, e)); null}
		}

		if (null != user && user.password == password) { user.words }
		else Nil
	}

	private[this] def processNewWords(host: String, port: Int, username: String, 
		password: String, word: String, 
		handler: (String, java.util.List[NewWord]) => java.util.List[NewWord])
	{
		val dao = new NewWordBookDao(new MongoServer(host, port) :: Nil)
		val user = try {
			val rs = dao.findByCondition(newCondition("username", username))
			if (rs.hasNext) rs.next else null
		} catch { case e: Throwable => {
			logger warn ("find iciba user eror: {}, {}", Array(username, e)); null}
		}

		if (null != user && user.password == password) {
			user.words = handler(word, user.words)
			dao.updateOne(newCondition("username", user.username), 
				newCondition("$set", newCondition("words", user.words)))
		}
	}

	private[this] def addWordOpt(word: String, words: java.util.List[NewWord]
		): java.util.List[NewWord] = 
	{
		if (null == words || words.length == 0) {
			NewWord(word, 1) :: Nil
		} else {
			val l = (for (w <- words) yield w.word)
			if (l contains word) {
				var c = words.get(l indexOf word)
				c.count = c.count + 1
			} else {
				words.add(NewWord(word, 1))
			}
			words.sortWith(_.count > _.count)
		}
	}

	private[this] def removeWordOpt(word: String, words: java.util.List[NewWord]
		): java.util.List[NewWord] = 
	{
		if (null == words && words.length == 0) { Nil } 
		else { for (w <- words; if (w.word != word)) yield w }
	}

	def addNewWord(host: String, port: Int, username: String, password: String, 
		word: String)
	{ processNewWords(host, port, username, password, word, addWordOpt) }

	def removeNewWord(host: String, port: Int, username: String, password: String, 
		word: String)
	{ processNewWords(host, port, username, password, word, removeWordOpt) }

}


/*

/home/morgan/GRE.txt


import jadecrawler.website.IcibaCrawler
import scala.io.Source
val dao = IcibaCrawler.getDao("www.jade-dungeon.net", 27017)
def transWord(word: String) {
if (null == IcibaCrawler.loadLocal(dao, word)) { 
val rec = jadecrawler.website.IcibaCrawler.process(word)
if (null != rec) IcibaCrawler.saveLocal(dao, rec)
}
}
scala.io.Source.fromFile("/home/morgan/GRE.txt").getLines.foreach((s) =>{transWord(s);Thread.sleep(200)})

*/
