package jadeutils.web

import java.io.IOException
import java.io.PrintWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException

import org.slf4j.LoggerFactory
import org.slf4j.Logger

class DispatherServlet extends HttpServlet {
	lazy val logger = DispatherServlet.logger

		@throws(classOf[IOException])
		@throws(classOf[ServletException])
    override def doGet(request: HttpServletRequest, response: HttpServletResponse)
    {
			val path = formalizePath(request)
			response.setContentType("text/html");
			val out: PrintWriter = response.getWriter()
			out.println("<html>")
			out.println("<head>")
			out.println("<title>Hello World!</title>")
			out.println("</head>")
			out.println("<body>")
			out.println("<h1>Hello! This is a Scala Dispather Servlet!</h1>")
			out.println("path: " + path + "<br/>")
			out.println("</body>")
			out.println("</html>")
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
}



class PathPatten(patten: String) {
	lazy val logger = PathPatten.logger

	// regex for draw param's value in path
	val valuePtn = java.util.regex.Pattern.compile(
		PathPatten.paramPtn.replaceAllIn(patten, "(.*)"))
	// param's name in path
	val params = PathPatten.paramPtn.findAllIn(patten).toList
	val keys = for (item <- params if item.length > 4)
		yield item.substring(2, item.length -1)

	def matchPath(path: String): (Boolean, Map[String, String]) = {
		/* draw all param's value in url */
		val m = valuePtn.matcher(path)
		val isMatch = m.matches
		val values = if (isMatch && m.groupCount > 0) {
			for (i <- 1 to m.groupCount) yield m group i
		} else Nil
		/* make param's key-value map */
		val items = (Map.empty[String, String] /: (keys zip values)) (
			(a, b) => (a + (b._1 -> b._2)))
		(isMatch, items)
	}

}

object PathPatten {
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	// regex for draw param's name in path-patten
	private val paramPtn = """\$\{([^${}]+)\}""".r

}



trait BasicController
