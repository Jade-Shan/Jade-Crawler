package net.jadedungeon

import java.io.IOException
import java.io.PrintWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException

class Welcome extends HttpServlet {

		@throws(classOf[IOException])
		@throws(classOf[ServletException])
    override def doGet(request: HttpServletRequest, response: HttpServletResponse)
		{
			val basePath = request.getScheme() + "://" + request.getServerName() + 
				":" + request.getServerPort() + request.getContextPath() + "/"

			response.setContentType("text/html")
			val out: PrintWriter = response.getWriter()
			out.println("<html>")
			out.println("<head>")
			out.println("<title>Hello World!</title>")
			out.println("</head>")
			out.println("<body>")
			out.println("<h1>Hello! This is a Scala Servlet!</h1>")
			out.println(basePath + "<br/>")
			out.println(request.getRequestURI + "<br/>")
			out.println(request.getRequestURL + "<br/>")
			out.println(request.getServletPath + "<br/>")
			out.println("</body>")
			out.println("</html>")
		}
}

