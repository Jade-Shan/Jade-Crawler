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

trait BasicController
