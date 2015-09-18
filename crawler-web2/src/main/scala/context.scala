package jadecrawler.web2

import jadeutils.common.Logging
import jadeutils.common.EnvPropsComponent

trait DaoComponent extends Logging {
	this: DaoContext =>

	object DaoCtx {
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

trait DaoContext extends EnvPropsComponent with DaoComponent



trait ServiceComponent extends Logging {
	this: ServiceContext =>

	object ServiceCtx {
		val host: String = getProperty("mongo.host")
		val port: Int    = Integer.parseInt(getProperty("mongo.port"))

	}

}


trait ServiceContext extends EnvPropsComponent with ServiceComponent
