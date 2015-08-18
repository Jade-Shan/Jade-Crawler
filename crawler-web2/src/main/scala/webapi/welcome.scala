package jadecralwer.web2

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
        response.setContentType("text/html");
        val out: PrintWriter = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World!</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Hello! This is a Scala Servlet!</h1>");
        out.println("</body>");
        out.println("</html>");
    }
}

