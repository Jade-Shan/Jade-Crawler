package net.jadedungeon.blog

import jadeutils.common.Logging
import jadeutils.common.EnvPropsComponent

trait BlogRecDaoCompoment extends Logging {
	this: EnvPropsComponent =>

	object RecDaos {
		val host: String = getProperty("mongo.host")
		val port: Int    = Integer.parseInt(getProperty("mongo.port"))

		logDebug("----------- Creating jounaryRecordDao: {}, {}", host, port)
		val journalDao = new JournalDao(host, port)
		logDebug("----------- Creating galleryRecordDao: {}, {}", host, port)
		val galleryDao = new GalleryDao(host, port)
	}
}

trait BlogAppCtx extends EnvPropsComponent with BlogRecDaoCompoment
with BlogRecService with Logging 
{

	val cfgFile = "workout.properties"
	logDebug("----------- Loading props: {}", cfgFile)

	val envProps = new java.util.Properties()
	envProps.load(Thread.currentThread().getContextClassLoader()
		.getResourceAsStream(cfgFile))

	val cdn3rd = getProperty("cdn.3rd")
	val cdnjadeutils = getProperty("cdn.jadeutils")
	val cdnworkout = getProperty("cdn.workout")
	val appbasepath = getProperty("app.basepath")

}
