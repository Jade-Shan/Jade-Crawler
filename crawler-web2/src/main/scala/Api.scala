package jadecrawler.web2

import jadeutils.web.BasicController

class ApiDispather extends jadeutils.web.DispatherServlet 
{ ApiDispather.controllers = new IcibaApiController :: Nil }

object ApiDispather { var controllers: List[BasicController] = Nil }
