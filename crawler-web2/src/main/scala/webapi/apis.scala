package jadecrawler.web2

import jadeutils.web.DispatherServlet
import jadeutils.web.BasicController

class ApiDispather extends DispatherServlet {
	ApiDispather.controllers = new IcibaApiController :: Nil
}
object ApiDispather { var controllers: List[IcibaApiController] = Nil }


class IcibaApiController extends BasicController {
	val html = """<html><head><title>%s</title></head><body><h1>Hello! This is %s</h1>%s<br/>%s<br/></body></html>"""
	service("/${a}/${b}/${c}") {
		(info) => {
			info.response.setContentType("text/html");
			val out = info.response.getWriter()
			out.println(html.format("logic 1", "logic 1", info.request.getRequestURI, 
				info.params.toString))
		}
	}
}


/*

test-env-00:45:25.246 DEBUG [main] jadeutils.web.DispatherServlet$.jadeutils$web$DispatherServlet$$formalizePath:166- ctxPath: 
test-env-00:45:25.246 DEBUG [main] jadeutils.web.DispatherServlet$.jadeutils$web$DispatherServlet$$doLogic:127- Dispath Req: GET /jack/233/skinner
test-env-00:45:25.247 DEBUG [main] jadeutils.web.RequestPattern$.matchPath:41- match path   :/jack/233/skinner
test-env-00:45:25.247 DEBUG [main] jadeutils.web.RequestPattern$.matchPath:42- match pattern:/([^/]*)/([^/]*)/([^/]*)
test-env-00:45:25.249 DEBUG [main] jadeutils.web.RequestPattern$.matchPath:49- param value: Vector(jack, 233, skinner)

*/

