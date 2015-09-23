package net.jadedungeon.workout

import jadeutils.common.Logging
import jadeutils.common.EnvPropsComponent

trait WorkoutRecDaoComponent extends Logging {
	this: EnvPropsComponent =>

	object RecDaos {
		val host: String = getProperty("mongo.host")
		val port: Int    = Integer.parseInt(getProperty("mongo.port"))

		logger.debug("----------- Creating userAuthDao: {}, {}", host, port)
		val userAuthDao = new UserAuthDao(host, port)
		logger.debug("----------- Creating aerobicRecordDao: {}, {}", host, port)
		val aerobicRecordDao = new AerobicRecordDao(host, port)
		logger.debug("----------- Creating strengthRecordDao: {}, {}", host, port)
		val strengthRecordDao = new StrengthRecordDao(host, port)
	}

}



trait WorkoutAppCtx extends EnvPropsComponent with WorkoutRecDaoComponent 
with WorkoutRecService 
with Logging {

	val cfgFile = "workout.properties"
	logger.debug("----------- Loading props: {}", cfgFile)

	val envProps = new java.util.Properties()
	envProps.load(Thread.currentThread().getContextClassLoader()
		.getResourceAsStream(cfgFile))

	val cdn3rd = getProperty("cdn.3rd")
	val cdnjadeutils = getProperty("cdn.jadeutils")
	val cdnworkout = getProperty("cdn.workout")

}
