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

import net.jadedungeon.workout.WorkoutAppCtx
import net.jadedungeon.workout.WorkoutAuthController
import net.jadedungeon.workout.WorkoutRecController

import net.jadedungeon.blog.BlogRecController

class ApiDispather extends jadeutils.web.DispatherServlet with WorkoutAppCtx
{ 
	ApiDispather.controllers = IcibaApiController :: WorkoutRecController :: 
		WorkoutAuthController :: BlogRecController :: ForecastController :: Nil 

	@throws(classOf[IOException])
	@throws(classOf[ServletException])
	override protected[this] def doLogic(method: Method.Method, 
		request: HttpServletRequest, response: HttpServletResponse) 
	{
		request.setAttribute("cdn3rd", cdn3rd)
		request.setAttribute("cdnjadeutils", cdnjadeutils)
		request.setAttribute("cdnworkout", cdnworkout)
		request.setAttribute("appbasepath", appbasepath)
		super.doLogic(method, request, response)
	}
}

object ApiDispather { var controllers: List[BasicController] = Nil }
