package jadecrawler.webapi

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.LiftRules
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

import jadecrawler.website.IcibaCrawler

object IcibaApi extends RestHelper {
	val dbHost = "mongo.local-vm"
	val dbPort = 27017

	val dao = IcibaCrawler.getDao(dbHost , dbPort)

	def init() : Unit = {
		LiftRules.statelessDispatch.append(IcibaApi)
	}

	// add user:
	// use crawler
	// db.newwordbook.insert( { "username" : "user", "password" : "1234", "words" : []});
	//
	// http://localhost:8080/api/dictionary/addnewword/jade/qwer1234/tango
	serve("api"/ "dictionary" / "addnewword" prefix {
			case username :: password :: word :: Nil JsonGet _ => 
				addNewWord(username, password, word) })
	
	private[this] def addNewWord(username: String, password: String, 
		word: String) =
	{
		IcibaCrawler.addNewWord(dbHost, dbPort, username, password, word)

		("status" -> "success"): JValue
	}

	// http://localhost:8080/api/dictionary/removenewword/jade/qwer1234/tango
	serve("api"/ "dictionary" / "removenewword" prefix {
			case username :: password :: word :: Nil JsonGet _ => 
				removeNewWord(username, password, word) })
	
	private[this] def removeNewWord(username: String, password: String, 
		word: String) =
	{
		IcibaCrawler.removeNewWord(dbHost, dbPort, username, password, word)

		("status" -> "success"): JValue
	}

	// http://localhost:8080/api/dictionary/newwords/jade/qwer1234
	serve("api"/ "dictionary" / "newwords" prefix {
			case username :: password :: Nil JsonGet _ => 
				loadNewWords(username, password) })
	
	private[this] def loadNewWords(username: String, password: String) = {
		val ll = IcibaCrawler.loadNewWords(dbHost, dbPort, username, password)

		("result" -> (for (i <- 0 until ll.size) yield ll.get(i)).map(
			w => ("word" -> w.word))): JValue
	}

	// http://172.16.140.37:8080/api/dictionary/eng2chs/check
	serve("api"/ "dictionary" / "eng2chs" prefix {
			case word :: Nil JsonGet _ => transWord(word) })

	// https://github.com/json4s/json4s
	private[this] def transWord(word: String) = {

		val cache = IcibaCrawler.loadLocal(dao, word)

		val result = if (null != cache) { cache } else {
			val rec = jadecrawler.website.IcibaCrawler.process(word)
			IcibaCrawler.saveLocal(dao, rec)
			rec
		}

		("result" -> 
			("word" -> result.word) ~ 
			("pronunciations" -> 
				(for (i <- 0 until result.pronunciations.size) yield result.pronunciations.get(i)).map(
					p => (("str1" -> p.str1) ~ ("str2" -> p.str2) ~ ("str3" -> p.str3))))  ~ 
			("explantions" -> 
				(for (i <- 0 until result.explantions.size) yield result.explantions.get(i)).map(
					p => (("str1" -> p.str1) ~ ("str2" -> p.str2))))  ~ 
			("relatedWords" -> 
				(for (i <- 0 until result.relatedWords.size) yield result.relatedWords.get(i)).map(
					p => (("str1" -> p.str1) ~ ("str2" -> p.str2))))  ~ 
			("examples" -> 
				(for (i <- 0 until result.examples.size) yield result.examples.get(i)).map(
					p => (("str1" -> p.str1) ~ ("str2" -> p.str2) ~ ("str3" -> p.str3))))  ~ 
			("homoionyms" -> 
				(for (i <- 0 until result.homoionyms.size) yield result.homoionyms.get(i)).map(
					p => (("str" -> p.str) ~ ("s2dto" -> 
						(for (i <- 0 until p.s2dto.size) yield p.s2dto.get(i)).map(
							d => (("str1" -> d.str1) ~ ("str2" -> d.str2)))))))
		): JValue
	}

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
