package jadeutils.web

import scala.language.implicitConversions

class UrlMatcher(path: List[String]) {

	
}

object UrlMatcher {
	implicit def UrlMatcherWarpper(path: List[String]) = new UrlMatcher(path)
}
