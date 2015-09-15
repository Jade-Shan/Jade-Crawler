package jadecrawler.website

import scala.language.postfixOps

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.apache.commons.lang.StringUtils.isNotBlank

import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import jadeutils.common.JsoupUtils._


object YyetsRecListParser {
	import jadecrawler.dto.website.YyetsRecListDto

	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def parse(htmlStr: String): List[YyetsRecListDto] = {
		val doc = Jsoup parse htmlStr
		val lines = doc select "div.resource-showlist>ul>li.clearfix>div.fl-info>dl>dt.f14>strong>a"
		(for (l <- lines; rec = genRec(l) if rec != None) yield rec) toList;
	}

	private[this] def genRec(e: Element) = {
		val name = e.text
		val href = e.attr("href")
		val id = if (null != href && href.contains("/resource/")) {
			href.split("/resource/")(1) 
		} else null
		if (null != id) new YyetsRecListDto(id, name) else null
	}

}

object YyetsRecInfoParser {
	import jadecrawler.dto.website.YyetsRecInfoDto
	import jadecrawler.dto.website.YyetsLink

	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def parse(htmlStr: String, id: String, name: String): List[YyetsRecInfoDto] = {
		val doc = Jsoup parse htmlStr
		val items = doc select "div.middle-box>div.w>div.download-box>div.media-box>div.media-list>ul>li.clearfix"
		val itms = (for (i <- items; itm = genItem(i, id, name) if itm != None) yield itm) toList;
		if (null != itms && itms.size > 0) itms else Nil
	}

	def genItem(e: Element, id: String, name: String) = {
		import scala.collection.JavaConversions._
		val season = e attr "season"
		val episode = e attr "episode"
		val format = e attr "format"
		val filename = (e select "div.fl>a").attr("title")
		val size =     (e select "div.fl>font.f3").text
		val links =     e select "div.fr>a"
		val lks = (for (l <- links; lk = genLink(l) if lk != None) yield lk) toList;
		new YyetsRecInfoDto(id, name, season, episode, format, filename, size, 
			lks.filter(_ != null))
	}

	def genLink(e: Element) = {
		val linkType = e.text
		linkType match {
			case "旋风" => null
			case "小米" => null
			case "字幕" => null
			case "迅雷" => null
			case _ => new YyetsLink(linkType, e attr "href")
		}
	}

}

object YyetsCrawler {
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	val site = "www.zimuzu.tv"

	import scala.collection.JavaConversions._

	import jadeutils.mongo.MongoServer
	import jadeutils.mongo.BaseMongoDao
	import jadecrawler.dto.website.YyetsRecInfoDto

	class YyetsDao(serverList: java.util.List[MongoServer]) extends BaseMongoDao[YyetsRecInfoDto](serverList)

	import jadecrawler.net.HTTPUtil
	import jadecrawler.net.JadeHTTPResponse

	def login(username: String, password: String): String = {
		// do login by http get
		val resp = try {
			HTTPUtil.doPost("http://" + site + "/User/Login/ajaxLogin", 
				HTTPUtil.firefoxParams + ("Host" -> site),
				("account", username) :: ("password", password) :: ("remember", "1") :: 
				("url_back", "http://www.zimuzu.tv/user/sign") :: Nil)
		} catch { case e: Throwable => {
			logger warn ("login err: {}, {}, {}", Array(username, password, e)); }
			new JadeHTTPResponse(Nil, new Array[Byte](0))
		}
		logger.debug("login resp: {} ", resp.toString)

		// return new cookies
		if (null != resp && null != resp.cookies && null != resp.content) {
			val content: String = new String(resp.content)
			logger debug ("login result: {}", content)

			if (content contains "\\u767b\\u5f55\\u6210\\u529f\\uff01") {
				val cookies = for (c <- resp.cookies;
					item = (c.getOrElse("name", ""), c.getOrElse("value", ""))
					if (item._1 == "PHPSESSID" || item._1 == "GINFO" || item._1 == "GKEY"))
					yield item
				(for (c <- cookies; 
					line = "%s=%s".format(c._1, c._2)) yield line).reduceLeft(_ + "; " + _)
			} else null
		} else null
	}

	def checkin(cookies: String): String = {
		try {
			HTTPUtil.doGet("http://" + site + "/user/sign", 
				HTTPUtil.firefoxParams + ("Host" -> site) + ("Cookie" -> cookies))
		} catch { case e: Throwable => {
			logger warn ("checkin err: {}", e); }
			new JadeHTTPResponse(Nil, new Array[Byte](0))
		}

		Thread.sleep(18 * 1000)

		val resp = try {
			HTTPUtil.doGet("http://" + site + "/user/sign/dosign", 
				HTTPUtil.firefoxParams + ("Host" -> site) + ("Cookie" -> cookies))
		} catch { case e: Throwable => {
			logger warn ("checkin err: {}", e); }
			new JadeHTTPResponse(Nil, new Array[Byte](0))
		}

		logger.debug(resp.toString)
		val result = new String(resp.content)
		logger.debug(result)
		result
	}

	def process(floor: Int, cell: Int, cookies: String, retry: Int) {
		process((floor to cell).toList, cookies, retry)
	}

	def process(pages: List[Int], cookies: String, retry: Int) {
		for (pageNo <- pages) {
			val list = getListRecs(pageNo, cookies, retry)
			if (null == list || list.size < 1) {
				logger error ("list-page no result: {}", pageNo)
			} else {
				logger info ("list-page OK: {}, rec count : {}", pageNo, list.size)
				for (item <- list) { processItem(item, cookies, retry) }
			}
		}
	}

	import jadecrawler.dto.website.YyetsRecListDto
	def getListRecs(pageNo: Int, cookies: String, retry: Int): List[YyetsRecListDto] = {
		val listPage = try {
			val resp = HTTPUtil.doGet(
				"http://" + site + "/eresourcelist?page=%d&channel=&area=&category=&format=&year=&sort=" format pageNo, 
				HTTPUtil.firefoxParams + ("Host" -> site) + ("Cookie" -> cookies))
			if (null != resp && null != resp.content && resp.content.length > 0) {
				new String(resp.content) 
			} else {
				logger error ("No Page error, pageNo:{}", pageNo)
				""
			}
		} catch { case e: Throwable => {
			logger warn ("list-page http err: {}, {}", Array(pageNo, e)); null }
		}

		val list = if (null != listPage) {
			try {
				YyetsRecListParser.parse(listPage)
			} catch { case e: Throwable => {
				logger warn ("list-page parse err: {}, {}", Array(pageNo, e)); Nil }
			}
		} else { Nil }

		if (list != null && list.size > 0) {list}
		else if (retry > 0) {getListRecs(pageNo, cookies, retry - 1)}
		else {list}
	}

	def processItem(item: YyetsRecListDto, cookies: String, retry: Int) {
		Thread.sleep(1000)
		val infoList = getInfoRecs(item, cookies, retry)
		if (null == infoList || infoList.size < 1) {
			logger error ("item-page no link: {}", item)
		} else {
			logger info ("item-page parse OK: {}", item)
			val dao = new YyetsDao(new MongoServer("www.jade-dungeon.net", 27017) :: Nil)
			for (info <- infoList) {
				try {
					dao insert info
					Thread.sleep(100)
					logger info ("item-save OK: {}", info)
				} catch {
					case e: Throwable => logger error ("item-save error: {}, {}", Array(item, e))
				}
			}
			dao.close
		}
	}

	def getInfoRecs(item: YyetsRecListDto, cookies: String, retry: Int): List[YyetsRecInfoDto] = {
		val infoPage = 	try {
			val resp = HTTPUtil.doGet("http://" + site + "/resource/list/%s" format item.id, 
				HTTPUtil.firefoxParams + ("Host" -> site) + ("Cookie" -> cookies))
			if (null != resp && null != resp.content && resp.content.length > 0) {
				new String(resp.content) 
			} else ""
		} catch { case e: Throwable => {
			logger warn ("item-page http error: {}, {}", Array(item, e)); null}
		}

		val infoList = try {
			YyetsRecInfoParser.parse(infoPage, item.id, item.name)
		} catch { case e: Throwable => {
			logger warn ("item-page parse error: {}, {}", Array(item, e)); Nil}
		}

		if (infoList != null && infoList.size > 0) { infoList }
		else if (retry > 0) { getInfoRecs(item, cookies, retry - 1) }
		else { infoList }
	}

}


import org.apache.commons.cli.BasicParser
import org.apache.commons.cli.Options
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.ParseException
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.OptionBuilder
import org.apache.commons.cli.CommandLineParser

class YyetsCrawlerApp(val msg: String)

/*
 * mvn clean scala:run -Dexec.mainClass=jadecrawler.website.YyetsCrawlerApp -DaddArgs="-u|username|-p|password|-m|checkin|-s|1|-e|1"
 */
object YyetsCrawlerApp extends App {
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	object Module extends Enumeration {
		val Checkin = Value(10,"checkin")
		val Crawler = Value(20,"crawler")
	}

	val options = new Options();
	options.addOption("c", "cookies",  true, "cookies")
	options.addOption("u", "username", true, "username")
	options.addOption("p", "password", true, "passowrd")
	options.addOption("m", "module",  true, "module")
	options.addOption("s", "start",    true, "start page")
	options.addOption("e", "end",      true, "end page")

	val line: CommandLine = try {
		(new DefaultParser()).parse(options, args)
	} catch {
		case e: Throwable => { logger error ("params error: {} ", e); null }
	}
	val c: String = if (line.hasOption("cookies"))
	{ line.getOptionValue("cookies") } else null

	val username: String = if (line.hasOption("username"))
	{ line.getOptionValue("username") } else null

	val password: String = if (line.hasOption("password"))
	{ line.getOptionValue("password") } else null

	val m: String = if (line.hasOption("module"))
	{ line.getOptionValue("module") } else null

	val start: String = if (line.hasOption("start"))
	{ line.getOptionValue("start") } else null

	val end: String = if (line.hasOption("end"))
	{ line.getOptionValue("end") } else null

	logger debug ("{} {} {} {} {} {}", c.asInstanceOf[Object], username.asInstanceOf[Object], 
		password.asInstanceOf[Object], m.asInstanceOf[Object], start.asInstanceOf[Object], 
		end.asInstanceOf[Object])

	val cookies = if (null == c || c.length == 0) {
		YyetsCrawler.login(username, password)
	} else c
	logger debug ("new cookies: {}", cookies)

	val module = try { Module.withName(m) } catch {
		case _: Throwable => Module.Checkin 
	}
	logger debug ("new module: {}", module)

	var floor = try Integer.parseInt(start) catch { case _: Throwable => 1 }
	floor = if (floor > 2) floor else 1
	var cell = try Integer.parseInt(end) catch { case _: Throwable => 1 }
	cell = if (cell > floor) cell else floor

	if (null != cookies) {
		logger debug ("login success! cookies: {}", cookies)
		logger info ("Start work, {}, {}, {}", cookies.asInstanceOf[Object], 
			floor.asInstanceOf[Object], cell.asInstanceOf[Object])

		module match {
			case Module.Crawler => YyetsCrawler.process(floor, cell, cookies, 3)
			case _ => YyetsCrawler.checkin(cookies)
		}
	}


	// val resp = jadecrawler.net.HTTPUtil.doGet("http://www.zimuzu.tv/resource/list/10733", 
	// 	jadecrawler.net.HTTPUtil.firefoxParams + ("Host" -> "www.zimuzu.tv") + ("Cookie" -> cookies	))
	// import java.io._
	// val writer = new PrintWriter(new File("yyets.html"))
	// writer write (new String(resp.content))
	// writer.close()
	//
	// YyetsCrawler.processItem(new jadecrawler.dto.website.YyetsRecListDto("11103", "aaa"), cookies, 3)
	// YyetsCrawler.process(range._1, range._2, cookies, 3)

}

