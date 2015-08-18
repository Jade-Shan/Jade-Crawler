package jadeutils.scala.web

import java.io.IOException
import java.io.PrintWriter
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException

class DispatherServlet extends HttpServlet {

		@throws(classOf[IOException])
		@throws(classOf[ServletException])
    override def doGet(request: HttpServletRequest, response: HttpServletResponse)
    {
        response.setContentType("text/html");
        val out: PrintWriter = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>DispatherServlet</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>DispatherServlet</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}

