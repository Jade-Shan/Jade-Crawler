package net.jadedungeon

import java.io.IOException
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException

import jadeutils.web.BasicController
import jadeutils.web.Method
import jadeutils.web.Method

import net.jadedungeon.dictionary.IcibaApiController

import net.jadedungeon.workout.WorkoutAppCtx
import net.jadedungeon.workout.WorkoutRecController

class ApiDispather extends jadeutils.web.DispatherServlet with WorkoutAppCtx
{ 
	ApiDispather.controllers = IcibaApiController :: WorkoutRecController :: Nil 

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override protected[this] def doLogic(method: Method.Method, 
		request: HttpServletRequest, response: HttpServletResponse) 
	{
		request.setAttribute("cdnjadeutils", cdnjadeutils)
		request.setAttribute("cdnworkout", cdnworkout)
		super.doLogic(method, request, response)
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:8091")
	}
}

object ApiDispather { var controllers: List[BasicController] = Nil }
