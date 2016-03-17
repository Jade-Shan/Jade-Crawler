package net.jadedungeon.dictionary

import org.slf4j.LoggerFactory

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

import jadeutils.common.Logging

import jadeutils.web.BasicController
import jadeutils.web.Method._
import jadeutils.web.DispatherServlet.Foward
import jadeutils.web.DispatherServlet.Redirect

import jadecrawler.website.IcibaCrawler



object IcibaApiController extends BasicController with Logging {

	val envProps = new java.util.Properties()
	envProps.load(Thread.currentThread().getContextClassLoader()
		.getResourceAsStream("workout.properties"))
	val dbHost = envProps.getProperty("mongo.host")
	val dbPort = Integer.parseInt(envProps.getProperty("mongo.port"))

	val dao = IcibaCrawler.getDao(dbHost , dbPort)

	service("/api/dictionary/addnewword/${word}") {(info) => {
		val auth = decodeHttpBasicAuth(info.request.getHeader("Authorization"))
		logDebug("after auth check: {}", auth)

		if (auth._1) {
			IcibaCrawler.addNewWord(dbHost, dbPort, auth._2, auth._3, 
				info.params("word")(0))
			("status" -> "success"): JValue
		} else {
			("status" -> "error"): JValue
		}
	}}

	service("/api/dictionary/removenewword/${word}") {(info) => {
		val auth = decodeHttpBasicAuth(info.request.getHeader("Authorization"))
		logDebug("after auth check: {}", auth)

		if (auth._1) {
			IcibaCrawler.removeNewWord(dbHost, dbPort, auth._2, auth._3, 
				info.params("word")(0))
			("status" -> "success"): JValue
		} else {
			("status" -> "error"): JValue
		}
	}}

	service("/api/dictionary/newwords/") {(info) => {
		val auth = decodeHttpBasicAuth(info.request.getHeader("Authorization"))
		logDebug("after auth check: {}", auth)

		if (auth._1) {
			val ll = IcibaCrawler.loadNewWords(dbHost, dbPort, auth._2, auth._3)
			("status" -> "success") ~ 
			("result" -> (
					if (null != ll) for (i <- 0 until ll.size) yield ll.get(i) else Nil
				).map(w => ("word" -> w.word))): JValue
		} else {
			("status" -> "error"): JValue
		}
	}}

	service("/api/dictionary/eng2chs/${word}") {(info) => {
		val word = info.params("word")(0)
		val cache = IcibaCrawler.loadLocal(dao, word)

		val data = if (null != cache) { cache } else {
			val rec = jadecrawler.website.IcibaCrawler.process(word)
			IcibaCrawler.saveLocal(dao, rec)
			rec
			// new jadecrawler.dto.website.IcibaDto(word, 
			// 	new java.util.ArrayList[jadecrawler.dto.website.IcibaS3Dto](), 
			// 	new java.util.ArrayList[(jadecrawler.dto.website.IcibaS2Dto)](), 
			// 	new java.util.ArrayList[jadecrawler.dto.website.IcibaS2Dto](),
			// 	new java.util.ArrayList[jadecrawler.dto.website.IcibaS3Dto](), 
			// 	new java.util.ArrayList[jadecrawler.dto.website.IcibaHomoDto]())
		}

		logInfo("-------{}", data)

		("result" -> 
			("word" -> word) ~ 
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
					p.s2dto.get(i)).map( d => (("str1" -> d.str1) ~ ("str2" -> d.str2))))))) ~
			("sameWrds" -> (for (i <- 0 until data.sameWrds.size) yield
				data.sameWrds.get(i)).map(p =>
				(("str" -> p.str) ~ ("words" -> (for (i <- 0 until p.words.size) yield
					p.words.get(i)))))) ~
			("oppsites" -> (for (i <- 0 until data.oppsites.size) yield
				data.oppsites.get(i)).map(p =>
				(("str" -> p.str) ~ ("words" -> (for (i <- 0 until p.words.size) yield
					p.words.get(i)))))) ~
			("phrases" -> (for (i <- 0 until data.phrases.size) yield
				data.phrases.get(i)).map(p =>
				(("str1" -> p.str1) ~ ("str3" -> p.str3) ~ ("str2" -> p.str2)))) ~
			("slangys" -> (for (i <- 0 until data.slangys.size) yield
				data.slangys.get(i)).map(p =>
				(("str1" -> p.str1) ~ ("str3" -> p.str3) ~ ("str2" -> p.str2))))): JValue
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
