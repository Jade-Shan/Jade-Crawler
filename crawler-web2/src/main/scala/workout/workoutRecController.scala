package net.jadedungeon.workout

import org.json4s._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

import jadeutils.common.Logging

import jadeutils.web.BasicController
import jadeutils.web.Method._
import jadeutils.web.Foward
import jadeutils.web.Redirect

trait BaseWorkoutController extends BasicController with WorkoutAppCtx

object WorkoutAuthController extends BaseWorkoutController with Logging
{
	service("/api/workout/user/auth") {(info) => {
		try {
			val auth = decodeHttpBasicAuth(info.request.getHeader("Authorization"))
			logger.debug("after auth check: {}", auth)
			val username = auth._2
			val password = auth._3
			if (auth._1 && null != username && username.trim.length > 0) {
				val rec = RecDaos.userAuthDao.findAuth(username)
				logger.debug(rec.toString)
				if (rec.size > 0 && password == rec(0).password) {
					("status" -> "success") ~ ("auth" -> "success"): JValue
				} else {
					("status" -> "success") ~ ("auth" -> "fail") ~
					("reason" -> "not match"): JValue
				}
			} else {
				("status" -> "success") ~ ("auth" -> "fail") ~
				("reason" -> "no username"): JValue
			}
		} catch {
			case e: Exception => {
				logger.error(e.toString)
				("status" -> "error") ~ ("err" -> e.toString): JValue
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
		try {
			val user     = info.params("username")(0)
			val item     = info.params("workoutId")(0)
			val time     = Integer.parseInt(info.params("time")(0))
			val distance = Integer.parseInt(info.params("distance")(0))
			val calories = Integer.parseInt(info.params("calories")(0))
			val rec = new AerobicRecord(user, item, time, distance, calories, 
				System.currentTimeMillis)
			logger.debug(rec.toString)
			RecDaos.aerobicRecordDao.insert(rec)
			("status" -> "success"): JValue
		} catch {
			case e: Exception => {
				logger.error(e.toString)
				("status" -> "error") ~ ("err" -> e.toString): JValue
			}
		}
	}}

	service("/api/workout/recordStrengthRec") {(info) => {
		try {
			val user    = info.params("username")(0)
			val item    = info.params("workoutId")(0)
			val weight  = Integer.parseInt(info.params("weight")(0))
			val repeat  = Integer.parseInt(info.params("repeat")(0))
			val rec = new StrengthRecord(user, item, weight, repeat, 
				System.currentTimeMillis)
			logger.debug(rec.toString)
			RecDaos.strengthRecordDao.insert(rec)
			("status" -> "success"): JValue
		} catch {
			case e: Exception => {
				logger.error(e.toString)
				("status" -> "error") ~ ("err" -> e.toString): JValue
			}
		}
	}}
	
}
