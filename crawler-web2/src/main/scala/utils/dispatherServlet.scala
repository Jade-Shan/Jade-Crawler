package jadeutils.web

import java.io.IOException
import java.io.PrintWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException

import org.slf4j.LoggerFactory
import org.slf4j.Logger



object Method extends Enumeration {
	type Method = Value
	val ANY = Value(0,"ANY")
	val GET = Value(1,"GET")
	val POST = Value(2,"POST")
}



class RequestPattern(method: Method.Method, pattern: String) {
	lazy val logger = RequestPattern.logger

	def this(pattern: String) = this(Method.ANY, pattern)

	// regex for draw param's value in path
	val valuePtn = java.util.regex.Pattern.compile(
		RequestPattern.paramPtn.replaceAllIn(pattern, "([^/]*)"))
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
		val m = valuePtn.matcher(path)
		val isMatch = if (Method.ANY == this.method || this.method == method) 
				m.matches else false
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
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	// regex for draw param's name in path-pattern
	private val paramPtn = """\$\{([^${}]+)\}""".r

}


/* abstract of HTTP request and response info */
class DispatherInfo(val method: Method.Method, 
	val request: HttpServletRequest, val response: HttpServletResponse, 
	val params: Map[String, Array[String]])



/* abstract of send which kind of repueset pattern to which logic */
class BasicDispather(val pattern: RequestPattern, val logic: (DispatherInfo) => Unit) {
	override def toString = "{%s, %s}".format(pattern, logic)
}

object BasicDispather

/* abstract of MVC controller */
trait BasicController {

	def service(method: Method.Method, pattern: String)
		(logic: (DispatherInfo) => Unit) 
	{
		val dpth = new BasicDispather(new RequestPattern(method, pattern), logic)
		DispatherServlet.addDisPather(dpth)
	}

	def service(pattern: String) (logic: (DispatherInfo) => Unit) {
		service(Method.ANY, pattern)(logic)
	}

}



trait DispatherServlet extends HttpServlet {
	import scala.collection.JavaConversions.mapAsScalaMap

	lazy val logger = DispatherServlet.logger

	// def controllers: BasicController
	// def addController(ctler: BasicController)

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doGet(request: HttpServletRequest, response: HttpServletResponse)
	{
		this.doLogic(Method.GET, request, response)
	}

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override def doPost(request: HttpServletRequest, response: HttpServletResponse)
	{
		this.doLogic(Method.POST, request, response)
	}

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	private[this] def doLogic(method: Method.Method, 
		request: HttpServletRequest, response: HttpServletResponse) 
	{
		val path = formalizePath(request)
		logger.debug("Dispath Req: " + method + " " + path)

		var params = parseParamsFromRequest(request)
		logger.debug("query params: " + reqParamsAsString(params))

		// find a dispather that matchs the http request path
		// the result matchRec is the format like: (isMatch, logic, params)
		val matchRec = matchDispathers(method, path, DispatherServlet.dispathers)

		if (!matchRec._1) {
			response.setContentType("text/html");
			val out: PrintWriter = response.getWriter()
			out.println("<html>")
			out.println("<head>")
			out.println("<title>Error !!!</title>")
			out.println("</head>")
			out.println("<body>")
			out.println("<h1>No Match Dispath Pattern</h1>")
			out.println("path: " + path + "<br/>")
			out.println("</body>")
			out.println("</html>")
			out.flush()
		} else {
			for ((key, value) <- matchRec._3) {
				if (params.contains(key))
					params = params + (key ->  Array.concat(params(key), Array(value)))
				else
					params = params + (key -> Array(value))
			}
			logger.debug("all params: " + reqParamsAsString(params))
			matchRec._2(new DispatherInfo(method, request, response, params))
		}
	}

	private[this] def reqParamsAsString(params: Map[String, Array[String]]) = {
		var result = "{"
		for ((key, value) <- params) {
			result = result + key + " -> " + "[" + value.mkString + ", " + "], "
		}
		result = result + "}"
		result
	}

	private[this] def parseParamsFromRequest(request: HttpServletRequest) = {
		var result = Map.empty[String, Array[String]]
		if(null != request.getParameterMap) {
			val m: scala.collection.Map[String, Array[String]] = request.getParameterMap
			for ((key, value) <- m) { result = result + (key -> value) }
		}
		result
	}

	private[this] def matchDispathers(
		method: Method.Method, path: String, list: List[BasicDispather]): 
	(Boolean, (DispatherInfo) => Unit, Map[String, String]) = 
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

object DispatherServlet {
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	private var dispathers: List[BasicDispather] = Nil

	def addDisPather(dispather: BasicDispather) {
		logger.debug("add pattern to dispather list: " + dispather.pattern)
		dispathers = dispather :: dispathers
	}

}
