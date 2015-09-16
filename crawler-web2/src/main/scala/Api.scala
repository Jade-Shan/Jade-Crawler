package jadecrawler.web2



class ApiDispather extends jadeutils.web.DispatherServlet 
{ ApiDispather.controllers = new IcibaApiController :: Nil }

object ApiDispather { var controllers: List[IcibaApiController] = Nil }
