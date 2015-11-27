package jadecrawler.website

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties
import jadeutils.common.Logging

@RunWith(classOf[JUnitRunner])
class Dm5Test extends FunSuite with Logging{
	import scala.io.Source

	test("Test-scalatest") {
		val book = Dm5Parser.parseBook("aaa")
		//logInfo("{}", book)

		val bvols = for (vol <- book._3) yield Dm5Parser.parseVol(vol._1, vol._2, vol._3)

		bvols.foreach((vols) => {
//				val ll = for (v <- vols) yield Dm5Crawler.fetchPage(v._1, v._3)
//				val urls = for (l <- ll) yield Dm5Parser.parseImage(l)
//				println(urls)
			})

		for (vol <- book._3) Dm5Crawler.processVol(book._1, book._2, vol)

	}

}

object Dm5Test { 
}

