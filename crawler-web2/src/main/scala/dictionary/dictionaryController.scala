package jadecrawler.web2

import org.slf4j.LoggerFactory

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

import jadeutils.web.BasicController
import jadeutils.web.Method._
import jadeutils.web.Foward
import jadeutils.web.Redirect

import jadecrawler.website.IcibaCrawler



class IcibaApiController extends BasicController {

	val dbHost = "mongo.local-vm"
	val dbPort = 27017

	val dao = IcibaCrawler.getDao(dbHost , dbPort)

	service("/api/dictionary/addnewword/${word}") {(info) => {
		IcibaCrawler.addNewWord(dbHost, dbPort, info.params("username")(0),
			info.params("password")(0), info.params("word")(0))

		("status" -> "success"): JValue
	}}

	service("/api/dictionary/removenewword/${word}") {(info) => {
		IcibaCrawler.removeNewWord(dbHost, dbPort, info.params("username")(0),
			info.params("password")(0), info.params("word")(0))

		("status" -> "success"): JValue
	}}

	service("/api/dictionary/newwords/") {(info) => {
		val ll = IcibaCrawler.loadNewWords(dbHost, dbPort,
			info.params("username")(0), info.params("password")(0))

		("result" -> (for (i <- 0 until ll.size) yield ll.get(i)).map(
			w => ("word" -> w.word))): JValue
	}}

	service("/api/dictionary/eng2chs/${word}") {(info) => {
		val word = info.params("word")(0)
		val cache = IcibaCrawler.loadLocal(dao, word)

		val data = if (null != cache) { cache } else {
			val rec = jadecrawler.website.IcibaCrawler.process(word)
			IcibaCrawler.saveLocal(dao, rec)
			rec
		}

		("result" -> 
			("word" -> data.word) ~ 
			("pronunciations" -> (for (i <- 0 until data.pronunciations.size) yield 
				data.pronunciations.get(i)).map(p => 
				(("str1" -> p.str1) ~ ("str2" -> p.str2) ~ ("str3" -> p.str3)))) ~ 
			("explantions" -> (for (i <- 0 until data.explantions.size) yield 
				data.explantions.get(i)).map(p => 
				(("str1" -> p.str1) ~ ("str2" -> p.str2)))) ~ 
			("relatedWords" -> (for (i <- 0 until data.relatedWords.size) yield 
				data.relatedWords.get(i)).map(p => 
				(("str1" -> p.str1) ~ ("str2" -> p.str2)))) ~ 
			("examples" -> (for (i <- 0 until data.examples.size) yield 
				data.examples.get(i)).map(p => 
				(("str1" -> p.str1) ~ ("str2" -> p.str2) ~ ("str3" -> p.str3)))) ~ 
			("homoionyms" -> (for (i <- 0 until data.homoionyms.size) yield 
				data.homoionyms.get(i)).map(p => 
				(("str" -> p.str) ~ ("s2dto" -> (for (i <- 0 until p.s2dto.size) yield 
					p.s2dto.get(i)).map( d => (("str1" -> d.str1) ~ ("str2" -> d.str2)))))))
			): JValue
	}}

	service("/api/dictionary/removenewword/test") {(info) => {
		Foward("/WEB-INF/pages/test.jsp")
	}}

	service("/api/dictionary/removenewword/test2") {(info) => {
		Redirect("/index.html")
	}}
}

/*
 //scripts import word list

 import jadecrawler.website.IcibaCrawler
 import scala.io.Source

 val dao = IcibaCrawler.getDao(dbHost, dbPort)

 def transWord(word: String) {
	 if (null == IcibaCrawler.loadLocal(dao, word)) { 
		 val rec = jadecrawler.website.IcibaCrawler.process(word)
		 if (null != rec) IcibaCrawler.saveLocal(dao, rec)
		 }
 }

 scala.io.Source.fromFile("/home/morgan/CET-4.txt").getLines.foreach((s) =>{transWord(s);Thread.sleep(200)})
 */
