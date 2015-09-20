package jadeutils.web

import java.io.IOException
import java.io.PrintWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException

import org.json4s.JValue
import org.json4s.jackson.JsonMethods.compact
import org.json4s.jackson.JsonMethods.render

import jadeutils.common.Logging


/* abstrict of http method */
object Method extends Enumeration {
	type Method = Value
	val ANY     = Value(0, "ANY")
	val GET     = Value(1, "GET")
	val POST    = Value(2, "POST")
	val PUT     = Value(3, "PUT")
	val DELETE  = Value(4, "DELETE")
	val HEAD    = Value(5, "HEAD")
	val OPTIONS = Value(6, "OPTIONS")
	val TRACE   = Value(7, "TRACE")
}



case class Foward(url: String)

case class Redirect(url: String)


/* Pattern of http request want to match */
class RequestPattern(method: Method.Method, pattern: String) extends Logging {

	def this(pattern: String) = this(Method.ANY, pattern)

	// regex for draw param's value in path
	val valuePtn = pattern.replaceAll(RequestPattern.paramPtnStr, "([^/]*)").r
	// param's name in path
	val params = RequestPattern.paramPtn.findAllIn(pattern).toList
	val keys = for (item <- params if item.length > 3)
		yield item.substring(2, item.length -1)

	def matchPath(method: Method.Method, path: String): 
		(Boolean, Map[String, String]) = 
	{
		/* draw all param's value in url */
		logger.debug("match path   :" + path)
		logger.debug("match pattern:" + valuePtn.toString)
		val m = valuePtn.findAllIn(path)
		val isMatch = if (Method.ANY == this.method || this.method == method) 
				m.hasNext else false
		val values = if (isMatch && m.groupCount > 0) {
			for (i <- 1 to m.groupCount) yield m group i
		} else Nil
		logger.debug("param keys : " + keys.toString)
		logger.debug("param value: " + values.toString)
		/* make param's key-value map */
		val items = (Map.empty[String, String] /: (keys zip values)) (
			(a, b) => (a + (b._1 -> b._2)))
		(isMatch, items)
	}

	override def toString = "{%s, %s}".format(method, pattern)
}

object RequestPattern {
	// regex for draw param's name in path-pattern
	private val paramPtnStr = """\$\{([^${}]+)\}"""
	private val paramPtn = paramPtnStr.r
}


/* Some information about HTTP request and response */
class DispatherInfo(val method: Method.Method, 
	val request: HttpServletRequest, val response: HttpServletResponse, 
	val params: Map[String, Array[String]])

object DispatherInfo {

	def paramsToString(params: Map[String, Array[String]]) = {
		var recs = "["
		for ((key, value) <- params) {
			var str = value.mkString
			// str = if (str.length == 0) str else str.substring(0, str.length) 
			recs = recs + """{%s: "%s"},""".format(key, str)
		}
		recs = if (recs.length == 0) recs else recs.substring(0, recs.length - 1)
		recs + "]"
	}

}


/* mapping from 'request pattern' to 'process logic' */
class BasicDispather(val pattern: RequestPattern, val logic: (DispatherInfo) => Any) {
	override def toString = "{%s, %s}".format(pattern, logic)
}

// object BasicDispather



/* Servlet dispather request */
trait DispatherServlet extends HttpServlet with Logging {
	import scala.collection.JavaConversions.mapAsScalaMap

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doGet(request: HttpServletRequest, response: HttpServletResponse)
	{ this.doLogic(Method.GET, request, response) }

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doPost(request: HttpServletRequest, response: HttpServletResponse)
	{ this.doLogic(Method.POST, request, response) }

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doPut(request: HttpServletRequest, response: HttpServletResponse)
	{ this.doLogic(Method.PUT, request, response) }

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doDelete(request: HttpServletRequest, response: HttpServletResponse)
	{ this.doLogic(Method.DELETE, request, response) }

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doHead(request: HttpServletRequest, response: HttpServletResponse)
	{ this.doLogic(Method.HEAD, request, response) }

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doOptions(request: HttpServletRequest, response: HttpServletResponse)
	{ this.doLogic(Method.OPTIONS, request, response) }

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doTrace(request: HttpServletRequest, response: HttpServletResponse)
	{ this.doLogic(Method.TRACE, request, response) }

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	protected[this] def doLogic(method: Method.Method, 
		request: HttpServletRequest, response: HttpServletResponse) 
	{
		val path = formalizePath(request)
		logger.debug("Dispath Req: " + method + " " + path)

		var params = parseParamsFromRequest(request)
		logger.debug("req params: " + DispatherInfo.paramsToString(params))

		var headers = parseParamsFromHeader(request)
		logger.debug("req headers: " + DispatherInfo.paramsToString(headers))

		// find a dispather that matchs the http request path
		// the result matchRec is the format like: (isMatch, logic, params)
		val matchRec = matchDispathers(method, path, DispatherServlet.dispathers)

		if (!matchRec._1)
			response.sendError(404, "Resource not found: " + path )
		else {
			for ((key, value) <- matchRec._3) {
				if (params.contains(key))
					params = params + (key ->  Array.concat(params(key), Array(value)))
				else params = params + (key -> Array(value))
			}
			logger.debug("all params: " + DispatherInfo.paramsToString(params))
			matchRec._2(new DispatherInfo(method, request, response, params)) match {
				case Foward(newPath) => {
					logger.debug("forward: " + newPath)
					request.getRequestDispatcher(newPath).forward(request, response)
				}
				case Redirect(newPath) => {
					logger.debug("redirect: " + request.getContextPath + newPath) 
					response.sendRedirect(request.getContextPath + newPath)
				}
				case json: JValue => {
					response.setContentType("application/json")
					response.setHeader("Content-disposition", "inline")
					response.getWriter.println(compact(render(json)))
				}
				case _ => logger.error("Unknow Dispath result")
			}
		}
	}

	private[this] def parseParamsFromRequest(request: HttpServletRequest) = {
		var recs = Map.empty[String, Array[String]]
		if(null != request.getParameterMap) {
			val m: scala.collection.Map[String, Array[String]] = request.getParameterMap
			for ((key, value) <- m) { recs = recs + (key -> value) }
		}
		recs
	}

	private[this] def parseParamsFromHeader(request: HttpServletRequest) = {
		var recs = Map.empty[String, Array[String]]
		val ite = request.getHeaderNames
		while (ite.hasMoreElements) {
			val key = ite.nextElement
			var values: List[String] = Nil 
			val vite = request.getHeaders(key)
			while (vite.hasMoreElements) {
				values = vite.nextElement :: values
			}
			recs = recs + (key -> values.toArray)
		}
		recs
	}


	private[this] def matchDispathers(
		method: Method.Method, path: String, list: List[BasicDispather]): 
	(Boolean, (DispatherInfo) => Any, Map[String, String]) = 
	{
		if (Nil == list)  {
			(false, (info) => {}, Map.empty[String, String])
		} else {
			val rec = list.head.pattern.matchPath(method, path)
			if (rec._1) (rec._1, list.head.logic, rec._2)
			else matchDispathers(method, path, list.tail)
		}
	}

	private[this] def formalizePath(request: HttpServletRequest) = {
		val reqUri = request.getRequestURI
		val ctxPath = request.getContextPath
		logger.debug("reqUri: " + reqUri);
		logger.debug("ctxPath: " + ctxPath);
		if ((reqUri.indexOf(ctxPath + "/")) == 0) 
			reqUri.substring(ctxPath.length) else reqUri
	}

}

object DispatherServlet extends Logging {
	private var dispathers: List[BasicDispather] = Nil

	def addDisPather(dispather: BasicDispather) {
		logger.debug("add pattern to dispather list: " + dispather.pattern)
		dispathers = dispather :: dispathers
	}

}



/* abstract of MVC controller */
trait BasicController {

	def service(method: Method.Method, pattern: String)
		(logic: (DispatherInfo) => Any) 
	{
		val dpth = new BasicDispather(new RequestPattern(method, pattern), logic)
		DispatherServlet.addDisPather(dpth)
	}

	def service(pattern: String) (logic: (DispatherInfo) => Any) {
		service(Method.ANY, pattern)(logic)
	}


	/**
		* 取得HTTP Basic验证信息
		*/
	def decodeHttpBasicAuth(authStr: String): (Boolean, String, String) = {
		if (authStr.startsWith("Basic ")) {
			val base64 = authStr.substring(6, authStr.length).trim
			val arr = new String(java.util.Base64.getDecoder.decode(base64), 
				"UTF-8").split(":")
			(true, arr(0), arr(1))
		} else (false, null, null)
	}

}
