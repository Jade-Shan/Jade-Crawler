package jadecrawler.website

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import java.util.Properties

@RunWith(classOf[JUnitRunner])
class WebIcibaTest extends FunSuite {
	import scala.io.Source

	val logger = WebIcibaTest.logger

	// val icibaHtml = Source.fromFile("src/test/examples/iciba.html").mkString
	val icibaHtml = Source.fromFile("src/test/examples/iciba.html2").mkString

	test("Test-scalatest") {
		val rec = WebIcibaParser.parse(icibaHtml)
		logger.debug(rec.toString)
		assert(rec != null)
	}

	test("Test-dump-words") {
		// import jadecrawler.website.IcibaCrawler
		// import scala.collection.JavaConversions._
		// import java.io._

		// val dao = IcibaCrawler.getDao("localhost", 27017)

		//val writer = new PrintWriter(new File("aa.txt"))
		//for (page <- 0 until ((11366 +19999) / 20000)) for(w <- (dao.findByCondition(null).skip(page * 20000).limit(20000)).toList.toList) writer write (w.word +"\n")
		//	writer.close()

		// def addNewWord(word: String) { if (null == IcibaCrawler.loadLocal(dao, word)) { val rec = jadecrawler.website.IcibaCrawler.process(word); IcibaCrawler.saveLocal(dao, rec) } }

		// val aa = scala.io.Source.fromFile("cet-word.txt")
		// val lines = aa.getLines
		// for (line <- lines) { addNewWord(line) ; Thread.sleep(100)}
	}
}

object WebIcibaTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}
