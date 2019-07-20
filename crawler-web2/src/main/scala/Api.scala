package net.jadedungeon

import java.io.IOException
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException

import jadeutils.web.BasicController
import jadeutils.web.Method
import jadeutils.web.Method

import net.jadedungeon.forecast.ForecastController
import net.jadedungeon.dictionary.IcibaApiController


import jadeutils.common.Logging
import jadeutils.common.EnvPropsComponent

trait DictRecDaoCompoment extends Logging {
	this: EnvPropsComponent =>

	object RecDaos {

		import scala.collection.JavaConversions._

		val host: String = getProperty("mongo.host")
		val port: Int    = Integer.parseInt(getProperty("mongo.port"))
		val auth = getProperty("mongo.authList.jadedungeon").split("`") :: Nil

		logDebug("----------- Creating jounaryRecordDao: {}, {}, {}", host, port, auth)
		// val journalDao = new JournalDao(host, port, auth)
		logDebug("----------- Creating galleryRecordDao: {}, {}, {}", host, port, auth)
		// val galleryDao = new GalleryDao(host, port, auth)
	}
}

trait CrawlerAppCtx extends EnvPropsComponent with DictRecDaoCompoment
// with BlogRecService 
with Logging 
{

	val cfgFile = "crawler.properties"
	logDebug("----------- Loading props: {}", cfgFile)

	val envProps = new java.util.Properties()
	envProps.load(Thread.currentThread().getContextClassLoader()
		.getResourceAsStream(cfgFile))

	val cdn3rd = getProperty("cdn.3rd")
	val cdnjadeutils = getProperty("cdn.jadeutils")
	val cdncrawler = getProperty("cdn.crawler")
	val appbasepath = getProperty("app.basepath")

}





class ApiDispather extends jadeutils.web.DispatherServlet with CrawlerAppCtx
{ 
	ApiDispather.controllers = IcibaApiController :: ForecastController :: Nil 

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override protected[this] def doLogic(method: Method.Method, 
		request: HttpServletRequest, response: HttpServletResponse) 
	{
		request.setAttribute("cdn3rd", cdn3rd)
		request.setAttribute("cdnjadeutils", cdnjadeutils)
		request.setAttribute("cdncrawler", cdncrawler)
		request.setAttribute("appbasepath", appbasepath)
		super.doLogic(method, request, response)
	}
}

object ApiDispather { var controllers: List[BasicController] = Nil }
