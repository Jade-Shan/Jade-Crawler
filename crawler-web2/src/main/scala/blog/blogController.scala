package net.jadedungeon.blog

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

import jadeutils.common.Logging

import jadeutils.web.BasicController
import jadeutils.web.DispatherInfo
import jadeutils.web.Foward
import jadeutils.web.Method._
import jadeutils.web.Redirect

trait BaseBlogController extends BasicController with BlogAppCtx

object BlogRecController extends BaseBlogController with Logging {

	service("/api/blog/findJournal/${auth}/${page}") {(info) => {
		info.response.setHeader("Access-Control-Allow-Origin", "*")
		var auth = info.params("auth")(0)
		var page = info.params("page")(0).toInt
		try {
			val recs = findJournal(auth, page)
			logDebug("query reault: {}", recs)
			("status" -> "success") ~ ("page" -> page) ~ ("count" -> recs._1) ~ 
			("articles" -> (if (null != recs._2) { recs._2 } else Nil).map(
				r => ("time" -> r.time) ~("auth" -> r.auth) ~ ("title" -> r.title) ~
				("text" -> r.text))) : JValue
		} catch {
			case e: Exception => {
				e.printStackTrace()
				logError(e.toString)
				("status" -> "error") ~ ("err" -> e.toString): JValue
			}
		}
	}}

	service("/api/blog/recordJournal") {(info) => {
		try {
			var auth = info.params("auth")(0)
			var title = info.params("title")(0)
			var time = System.currentTimeMillis //(info.params("time")(0)).toLong
			var text = info.params("text")(0)

			val rec = new Journal(title, auth, time, text)
			logDebug("record Journal rec: {}" + rec)
			RecDaos.journalDao.insert(rec)
			("status" -> "success"): JValue
		} catch {
			case e: Exception => {
				e.printStackTrace()
				logError(e.toString)
				("status" -> "error") ~ ("err" -> e.toString): JValue
			}
		}
	}}

}

