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

object WorkoutRecController extends BaseWorkoutController with Logging 
{
	service("/page/workout/StrengtWorkout/details/${workoutId}"){(info) => {
		info.request.setAttribute("workoutId", info.params("workoutId"))
		Foward("/WEB-INF/pages/workout/recs/strengthDetail.jsp")
	}}

	service("/page/workout/AerobicWorkout/details/${workoutId}"){(info) => {
		info.request.setAttribute("workoutId", info.params("workoutId"))
		Foward("/WEB-INF/pages/workout/recs/areobicDetail.jsp")
	}}

	service("/api/workout/recordAerobicRec"){(info) => {
		try {
			val user     = info.params("username")(0)
			val item     = info.params("item")(0)
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

	service("/api/workout/recordStrengthRec"){(info) => {
		try {
			val user    = info.params("username")(0)
			val item    = info.params("item")(0)
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
