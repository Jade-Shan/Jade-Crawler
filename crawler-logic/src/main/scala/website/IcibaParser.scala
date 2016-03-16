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
import jadecrawler.dto.website.Opposite

import jadeutils.common.JsoupUtils._

import scala.collection.JavaConversions._

object WebIcibaParser extends Logging {

	def parse(htmlStr: String): Option[IcibaDto] = {
		import scala.collection.JavaConversions._

		val doc = Jsoup parse htmlStr

		val articles = doc select "div.info-article"

		val word = (doc select "div.in-base>div>h1.keyword").text
		logger trace word

		val prons = doc select "div.in-base>div>div>div.base-speak>span"
		val pronuctions = (for (p <- prons; rec = genPron(p) if rec != None) 
			yield { rec.getOrElse(null) }) toList;
		logger trace pronuctions.toString

		val exps = doc select "div.in-base>ul.base-list>li"
		val explations = (for (e <- exps; rec = genExps(e)) yield rec) toList;
		logger trace explations.toString

		val rlts1 = (for (r <- (doc select "li.change>p>span"); 
			rec = r.text if null != rec) yield rec) toList;
		val rlts2 = (for (r <- (doc select "li.change>p>a"); 
			rec = r.text if null != rec) yield rec) toList;
		val relatedWords = (for (r <- (rlts1 zip rlts2); 
			rec = new IcibaS2Dto(r._1, r._2) if null != rec) yield rec) toList;
		logger trace relatedWords.toString

		val examps = doc select "div.article-section>div.section-p"
		val examples = (for (r <- examps; rec = genExamps(r)) yield rec) toList;
		logger trace examples.toString

		val homos = getArticleByType(articles, (e) => {
				val arr = e select "ul.article-list>li.current"
				arr.length > 0 && arr.get(0).text.contains("同义词辨析")
			})
		val homos2 = (for (e <- homos; 
			a <- e select "div.collins-section>div.section-prep>div.prep-order") yield a).toList
		val homoionyms = (for (r <- homos2; rec = genHomo(r) if null != rec) 
			yield rec) toList;
		logger trace homoionyms.toString

		val pha = getArticleByType(articles, (e) => {
				val arr = e select "ul.article-list>li.current"
				arr.length > 0 && arr.get(0).text.contains("词组搭配")
			})
		val pha1 = (for (e <- pha; a <- e select "div.collins-section") yield a).toList
		val phrases = (for (r <- pha1; rec = genPhrase(r) if null != rec) 
			yield rec) toList;
		logger trace phrases.toString

		val sln = getArticleByType(articles, (e) => {
				val arr = e select "ul.article-list>li.current"
				arr.length > 0 && arr.get(0).text.contains("常用俚语")
			})
		val sln1 = (for (e <- sln; a <- e select "div.collins-section>div.section-prep") yield a).toList
		val slangys = (for (r <- sln1; rec = genSlangy(r) if null != rec) 
			yield rec) toList;
		logger trace phrases.toString

		val opw = getArticleByType(articles, (e) => {
				val arr = e select "ul.article-list>li.current"
				arr.length > 0 && arr.get(0).text.contains("同反义词")
			})
		val opw1 =  (for (e <- opw; a <- e select "div.article") yield a).toList
		val opGrp = if (null != opw1 && opw1.size > 0) genOpposite(opw1(0)) else (Nil, Nil)

		Some(new IcibaDto(word, pronuctions, explations, relatedWords, examples,
			homoionyms, opGrp._1, opGrp._2, phrases, slangys))
	}

	def getArticleByType(list: Elements, typeFunc: (Element) => Boolean) = (
		for (e <- list if typeFunc(e)) yield e).toList 

	val pronPattern = """^(\S+)\s?\[(.+)\]$""".r
	def genPron(e: Element) = e.text match {
		case pronPattern(a, b) => Some(new IcibaS3Dto(a, b, ""))
		case _ => None
	}

	def genExps(e: Element) = new IcibaS2Dto((e select "span.prop").text, 
		(e select "p").text)

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
		val chs = (e select "div.p-container>p.p-chinese").text
		val eng = (e select "div.p-container>p.p-english").text
		new IcibaS3Dto(eng, chs, "")
	}

	def genHomo(e: Element) = {
		import scala.collection.JavaConversions._

		val mean = (e select "p>span.family-chinese").text
		val words = e select "ul.text-sentence>li.item"
		val recs: java.util.List[IcibaS2Dto] = (for (w <- words; c = (w select "a", w select "span.family-chinese")) yield new IcibaS2Dto(c._1.text, c._2.text)).toList
		if (isNotBlank(mean)) new IcibaHomoDto(mean, recs) else null
	}

	def genSlangy(e: Element) = {
		import scala.collection.JavaConversions._

		val word = (e select "div.section-prep>div.prep-order>p.family-english").text
		val mean = e select "div.text-sentence>div.sentence-item"
		var a = ""
		var b = ""
		for (m <- mean) {
			if (m.attr("class").contains("no-icon") && m.text.size > 1) {
				a = a + "\t" + m.text
			} else if (!m.attr("class").contains("no-icon")) {
				b = b + m.select("p.family-english").text + "\t" +
					m.select("p.family-chinese").text + "\n"
			}
		}
		new IcibaS3Dto(word, a, b)
	}

	def genPhrase(e: Element) = {
		import scala.collection.JavaConversions._

		val word = (e select "div.section-h>p>span.family-english").text
		val mean = (e select "div.section-prep>div.prep-order>p>span.family-english").text
		val exp = (e select "div.section-prep>div.prep-order>div.text-sentence").text

		val blk = e select "div.text-sentence>div.sentence-item"
		var b = ""
		for (m <- blk) {
				b = b + m.select("p.family-english").text + "\t" +
					m.select("p.family-chinese").text + "\n"
		}

		if (isNotBlank(mean)) new IcibaS3Dto(word, mean, b) else null
	}

	def genOpposite(e: Element): (List[Opposite], List[Opposite]) = {
		import scala.collection.JavaConversions._

		var la: List[Opposite] = Nil
		var lb: List[Opposite] = Nil

		var stype = ""
		for (o <- e select "div.article>div") {
			if (o.text.contains("同义词")) stype =      "同义词"
			else if (o.text.contains("反义词")) stype = "反义词"
			else { 
				val s = o.select("div.prep-order>p.family-chinese").text
				val ws = (for(c <- o.select("div.sentence-item>p.family-chinese>a")) yield c.text).toList
				if (null != s && s.size > 0 && ws.size > 0) {
					if (stype == "同义词") la = new Opposite(s, ws) :: la
					if (stype == "反义词") lb = new Opposite(s, ws) :: lb
				}
			}
		}

		(la, lb)
	}

}

object IcibaCrawler extends Logging {
	import scala.collection.JavaConversions._

	val site = "www.iciba.com"

	def process(word: String) = {
		val page = try {
			import jadecrawler.net.HTTPUtil
			val resp = HTTPUtil.doGet(
				"http://" + site + "/%s" format word,
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
						logger debug ("iciba page parse OK: {}", result.word)
						try { 
							dao insert result 
							logger debug ("iciba page save OK: {}", result.word)
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
