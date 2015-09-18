package net.jadedungeon

import jadeutils.web.BasicController
import net.jadedungeon.dictionary.IcibaApiController

class ApiDispather extends jadeutils.web.DispatherServlet 
{ ApiDispather.controllers = new IcibaApiController :: Nil }

object ApiDispather { var controllers: List[BasicController] = Nil }
