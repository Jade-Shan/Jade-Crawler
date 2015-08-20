package jadeutils.web

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.net.URI
import java.util.Properties

@RunWith(classOf[JUnitRunner])
class UrlTest extends FunSuite {

	test("Test-Url-Prefix") {
		ExampleTest.logger.debug("test scalatest")
		val uri = new URI("http://example.com/a%2Fb%3Fc/aaa/bbb/ccc/ddd");

		uri.getRawPath().split("/").foreach(System.err.println(_))
		assert(2 > 1)
	}

	val p1 = new PathPatten("/${username}/${userid}/${nickname}")
	val u1 = "/jack/233/skinner"

	val p2 = new PathPatten("/${username}/${userid}/${nickname}.html")
	val u2 = "/jack/233/skinner.html"

	val p3 = new PathPatten("/aaa/${username}/bbb/${userid}/ccc/${nickname}.html")
	val u3 = "/aaa/jack/bbb/233/ccc/skinner.html"

	val p4 = new PathPatten("/aaa/bbb/ccc.html")
	val u4 = "/aaa/bbb/ccc.html"

	test("Test-Path-Patten-params") {
		assert(p1.params == "${username}" :: "${userid}" :: "${nickname}" :: Nil)
		assert(p2.params == "${username}" :: "${userid}" :: "${nickname}" :: Nil)
		assert(p3.params == "${username}" :: "${userid}" :: "${nickname}" :: Nil)
		assert(p4.params == Nil)
	}

	test("Test-Path-Patten-keys") {
		assert(p1.keys == "username" :: "userid" :: "nickname" :: Nil)
		assert(p2.keys == "username" :: "userid" :: "nickname" :: Nil)
		assert(p3.keys == "username" :: "userid" :: "nickname" :: Nil)
		assert(p4.keys == Nil)
	}

	test("Test-Path-Patten-valuePtn") {
		assert(p1.valuePtn.toString == "/(.*)/(.*)/(.*)")
		assert(p2.valuePtn.toString == "/(.*)/(.*)/(.*).html")
		assert(p3.valuePtn.toString == "/aaa/(.*)/bbb/(.*)/ccc/(.*).html")
		assert(p4.valuePtn.toString == "/aaa/bbb/ccc.html")
	}

	test("Test-Path-Patten-values") {
		assert(p1.matchPath(u1) == 
			(true, Map("username" -> "jack", "userid" -> "233", 
				"nickname" -> "skinner")))
		assert(p2.matchPath(u2) == 
			(true, Map("username" -> "jack", "userid" -> "233", 
				"nickname" -> "skinner")))
		assert(p3.matchPath(u3) == 
			(true, Map("username" -> "jack", "userid" -> "233", 
				"nickname" -> "skinner")))
		assert(p4.matchPath(u4) == (true, Map()))
	}

}

object ExampleTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
