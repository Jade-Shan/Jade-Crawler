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
		RequestPattern.paramPtn.replaceAllIn(pattern, "(.*)"))
	// param's name in path
	val params = RequestPattern.paramPtn.findAllIn(pattern).toList
	val keys = for (item <- params if item.length > 4)
		yield item.substring(2, item.length -1)

	def matchPath(method: Method.Method, path: String): 
		(Boolean, Map[String, String]) = 
	{
		/* draw all param's value in url */
		val m = valuePtn.matcher(path)
		val isMatch = if (Method.ANY == this.method || this.method == method) 
				m.matches else false
		val values = if (isMatch && m.groupCount > 0) {
			for (i <- 1 to m.groupCount) yield m group i
		} else Nil
		/* make param's key-value map */
		val items = (Map.empty[String, String] /: (keys zip values)) (
			(a, b) => (a + (b._1 -> b._2)))
		(isMatch, items)
	}

}

object RequestPattern {
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	// regex for draw param's name in path-pattern
	private val paramPtn = """\$\{([^${}]+)\}""".r

}


/* abstract of HTTP request and response info */
class DispatherInfo(method: Method.Method, 
	req: HttpServletRequest, resp: HttpServletResponse, 
	params: Map[String, String])

object DispatherInfo


/* abstract of send which kind of repueset pattern to which logic */
class BasicDispather(val pattern: RequestPattern, val logic: (DispatherInfo) => Unit)

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

	private[this] def formalizePath(request: HttpServletRequest) = {
		val reqUri = request.getRequestURI
		val ctxPath = request.getContextPath
		logger.debug("reqUri: " + reqUri);
		logger.debug("ctxPath: " + ctxPath);
		if ((reqUri.indexOf(ctxPath + "/")) == 0) 
			reqUri.substring(ctxPath.length) else reqUri
	}

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	private[this] def doLogic(method: Method.Method, 
		request: HttpServletRequest, response: HttpServletResponse) 
	{
		val path = formalizePath(request)
		DispatherServlet.dispathers.find(_.pattern.matchPath(method, path)._1)
		logger.debug("Dispath Req: " + method + " " + path)
		// response.setContentType("text/html");
		// val out: PrintWriter = response.getWriter()
		// out.println("<html>")
		// out.println("<head>")
		// out.println("<title>Hello World!</title>")
		// out.println("</head>")
		// out.println("<body>")
		// out.println("<h1>Hello! This is a Scala Dispather Servlet!</h1>")
		// out.println("path: " + path + "<br/>")
		// out.println("</body>")
		// out.println("</html>")
	}

}

object DispatherServlet {
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	private var dispathers: List[BasicDispather] = Nil

	def addDisPather(dispather: BasicDispather) {
		dispathers = dispather :: dispathers
	}

}
