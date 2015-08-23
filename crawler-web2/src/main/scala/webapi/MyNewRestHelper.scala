package jadecrawler.web2

import org.slf4j.LoggerFactory

import jadeutils.web.BasicController
import jadeutils.web.DispatherInfo.paramsToString
import jadeutils.web.DispatherServlet

import jadeutils.common.JsonArray.JArr
import jadeutils.common.JsonObject.JObj

import jadecrawler.website.IcibaCrawler


class MyNewController extends BasicController {
	lazy val logger = MyNewController.logger

	val dbHost = "mongo.local-vm"
	val dbPort = 27017

	val dao = IcibaCrawler.getDao(dbHost , dbPort)

	service("/api/dictionary/addnewword/${word}") { (info) => {
		IcibaCrawler.addNewWord(dbHost, dbPort, info.params("username")(0),
			info.params("password")(0), info.params("word")(0))

		info.response.setContentType("application/json");
		info.response.setHeader("Content-disposition", "inline");
		val out = info.response.getWriter()
		val result = JObj ~ ("status", "success")
		out.println(result.toString)
	}}

	service("/api/dictionary/removenewword/${word}") { (info) => {
		IcibaCrawler.removeNewWord(dbHost, dbPort, info.params("username")(0),
			info.params("password")(0), info.params("word")(0))

		info.response.setContentType("application/json");
		info.response.setHeader("Content-disposition", "inline");
		val out = info.response.getWriter()
		val result = JObj ~ ("status", "success")
		out.println(result.toString)
	}}

	service("/api/dictionary/newwords/") { (info) => {
		val ll = IcibaCrawler.loadNewWords(dbHost, dbPort,
			info.params("username")(0), info.params("password")(0))
		logger.debug(ll.toString)

		val words = JArr
		logger.debug(words.toString)
		for (i <- 0 until ll.size) words ~~ (JObj ~ ("word", ll.get(i).word))
			logger.debug(words.toString)

		info.response.setContentType("application/json");
		info.response.setHeader("Content-disposition", "inline");
		val out = info.response.getWriter()
		val result = JObj ~ ("result", words)
		out.println(result.toString)
	}}

	service("/api/dictionary/eng2chs/${word}") { (info) => {
		val word = info.params("word")(0)
		val cache = IcibaCrawler.loadLocal(dao, word)

		val data = if (null != cache) { cache } else {
			val rec = jadecrawler.website.IcibaCrawler.process(word)
			IcibaCrawler.saveLocal(dao, rec)
			rec
		}

		val pronunciations = JArr
		for (i <- 0 until data.pronunciations.size) pronunciations ~~ (JObj ~ 
			("str1", data.pronunciations.get(i).str1) ~ 
			("str2", data.pronunciations.get(i).str2) ~ 
			("str3", data.pronunciations.get(i).str3))

		val explantions = JArr
		for (i <- 0 until data.explantions.size) explantions ~~ (JObj ~ 
			("str1", data.explantions.get(i).str1) ~ 
			("str2", data.explantions.get(i).str2))

		val relatedWords = JArr
		for (i <- 0 until data.relatedWords.size) relatedWords ~~ (JObj ~ 
			("str1", data.relatedWords.get(i).str1) ~ 
			("str2", data.relatedWords.get(i).str2))

		val examples = JArr
		for (i <- 0 until data.examples.size) examples ~~ (JObj ~ 
			("str1", data.examples.get(i).str1) ~ 
			("str2", data.examples.get(i).str2) ~ 
			("str3", data.examples.get(i).str3))

		val homoionyms = JArr
		for (i <- 0 until data.homoionyms.size) {
			val s2dto = JArr
			for (j <- 0 until data.homoionyms.get(i).s2dto.size) s2dto ~~ (JObj ~ 
				("str1", data.homoionyms.get(i).s2dto.get(j).str1) ~ 
				("str2", data.homoionyms.get(i).s2dto.get(j).str2))
			homoionyms ~~ (JObj ~ ("str", data.homoionyms.get(i).str) ~ 
				("s2dto", s2dto))
		}

		info.response.setContentType("application/json");
		info.response.setHeader("Content-disposition", "inline");
		val out = info.response.getWriter()
		val result = JObj ~ ("result", JObj ~ 
			("word", word) ~ ("pronunciations", pronunciations) ~
			("explantions", explantions) ~ ("relatedWords", relatedWords) ~
			("examples", examples) ~ ("homoionyms", homoionyms))
		out.println(result.toString)
	}}

}

object MyNewController { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)
}
