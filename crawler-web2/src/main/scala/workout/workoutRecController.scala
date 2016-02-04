package net.jadedungeon.workout

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

import jadeutils.common.Logging

import jadeutils.web.BasicController
import jadeutils.web.DispatherInfo
import jadeutils.web.DispatherServlet.Foward
import jadeutils.web.DispatherServlet.Redirect
import jadeutils.web.Method._

trait BaseWorkoutController extends BasicController with WorkoutAppCtx

object WorkoutAuthController extends BaseWorkoutController with Logging
{
	/**
		* (isAuth, isSuccess)
		*/
	def auth(info: DispatherInfo): (Boolean, Boolean, String) = {
		val auth = decodeHttpBasicAuth(info.request.getHeader("Authorization"))
		logDebug("after auth check: {}", auth)
		val username = auth._2
		val password = auth._3
		if (auth._1 && null != username && username.trim.length > 0) try {
			val rec = RecDaos.userAuthDao.findAuth(username)
			logDebug("match user: {}", rec)
			if (rec.size > 0 && password == rec(0).password) {
				(true, true, username)
			} else (true, false, username)
		} catch {
			case e: Exception => {
				logError(e.toString)
				(true, false, username)
			}
		} else (false, false, username)
	}

	service("/api/workout/user/auth") {(info) => {
		auth(info) match {
			case (true, true, _) => {
				("status" -> "success") ~ ("auth" -> "success"): JValue
			}
			case _ => {
				("status" -> "success") ~ ("auth" -> "fail") ~ 
				("reason" -> "not match"): JValue
			}
		}
	}}
}

object WorkoutRecController extends BaseWorkoutController with Logging 
{
	service("/page/workout/index.html") {(info) => {
		Foward("/WEB-INF/pages/workout/index.jsp")
	}}

	service("/page/workout/strengthWorkout/list") {(info) => {
		Foward("/WEB-INF/pages/workout/recs/strengthList.jsp")
	}}

	service("/page/workout/aerobicWorkout/list") {(info) => {
		Foward("/WEB-INF/pages/workout/recs/areobicList.jsp")
	}}

	service("/page/workout/strengthWorkout/details/${workoutId}") {(info) => {
		info.request.setAttribute("workoutId", info.params("workoutId")(0))
		Foward("/WEB-INF/pages/workout/recs/strengthDetail.jsp")
	}}
	
	service("/page/workout/aerobicWorkout/details/${workoutId}") {(info) => {
		info.request.setAttribute("workoutId", info.params("workoutId")(0))
		Foward("/WEB-INF/pages/workout/recs/areobicDetail.jsp")
	}}

	service("/api/workout/recordAerobicRec") {(info) => {
		WorkoutAuthController.auth(info) match {
			case (true, true, user) => try {
				val item     = info.params("workoutId")(0)
				val time     = info.params("time")(0).toInt
				val distance = info.params("distance")(0).toDouble
				val calories = info.params("calories")(0).toInt
				val logTime = info.params("logTime")(0).toLong
				val rec = new AerobicRecord(user, item, time, distance, calories, 
					// System.currentTimeMillis
					logTime)
				logDebug("record aero rec: {}", rec)
				RecDaos.aerobicRecordDao.insert(rec)
				("status" -> "success"): JValue
			} catch {
				case e: Exception => {
					logError(e.toString)
					("status" -> "error") ~ ("err" -> e.toString): JValue
				}
			}
			case _ => ("status" -> "error") ~ ("err" -> "need login"): JValue
		}
	}}

	service("/api/workout/recordStrengthRec") {(info) => {
		WorkoutAuthController.auth(info) match {
			case (true, true, user) => try {
				val item    = info.params("workoutId")(0)
				val weight  = info.params("weight")(0).toDouble
				val repeat  = info.params("repeat")(0).toInt
				val logTime = info.params("logTime")(0).toLong
				val rec = new StrengthRecord(user, item, weight, repeat, 
					// System.currentTimeMillis
					logTime)
				logDebug("record strength rec: {}" + rec)
				RecDaos.strengthRecordDao.insert(rec)
				("status" -> "success"): JValue
			} catch {
				case e: Exception => {
					logError(e.toString)
					("status" -> "error") ~ ("err" -> e.toString): JValue
				}
			}
			case _ => ("status" -> "error") ~ ("err" -> "need login"): JValue
		}
	}}

	service("/api/workout/findAerobicRec") {(info) => {
		WorkoutAuthController.auth(info) match {
			case (true, true, user) => try {
				val item     = info.params("workoutId")(0)
				val logTimeFloor = (info.params("logTimeFloor")(0)).toLong
				val logTimeCeil  = (info.params("logTimeCeil")(0)).toLong

				val recs = findAerobicRecs(user, item, logTimeFloor, logTimeCeil)
				logDebug("query result: {}", recs)
				("status" -> "success") ~ ("result" -> 
					(if (null != recs) { 
							var oRecs = recs.sortWith(_.logTime < _.logTime)
							for (i <- 0 until oRecs.size) yield oRecs(i) 
							} else Nil).map(
						r => ("user" -> r.user) ~ ("item" -> r.item) ~ ("time" -> r.time) ~ 
						("distance" -> r.distance) ~ ("calories" -> r.calories) ~ 
						("logTime" -> r.logTime)
					)) : JValue 
			} catch {
				case e: Exception => {
					logError(e.toString)
					("status" -> "error") ~ ("err" -> e.toString): JValue
				}
			}
			case _ => ("status" -> "error") ~ ("err" -> "need login"): JValue
		}
	}}

	service("/api/workout/findStrengthRec") {(info) => {
		WorkoutAuthController.auth(info) match {
			case (true, true, user) => try {
				val item    = info.params("workoutId")(0)
				val logTimeFloor = (info.params("logTimeFloor")(0)).toLong
				val logTimeCeil  = (info.params("logTimeCeil")(0)).toLong

				val recs = findStrengthRecs(user, item, logTimeFloor, logTimeCeil)
				logDebug("query result: {}", recs)
				("status" -> "success") ~ ("result" -> 
					(if (null != recs) {
							var oRecs = recs.sortWith(_.logTime < _.logTime)
							for (i <- 0 until oRecs.size) yield oRecs(i) 
							} else Nil).map(
						r => ("user" -> r.user) ~ ("item" -> r.item) ~ ("weight" -> r.weight) ~ 
						("repeat" -> r.repeat) ~ ("logTime" -> r.logTime)
					)) : JValue 
			} catch {
				case e: Exception => {
					logError(e.toString)
					("status" -> "error") ~ ("err" -> e.toString): JValue
				}
			}
			case _ => ("status" -> "error") ~ ("err" -> "need login"): JValue
		}
	}}

}
