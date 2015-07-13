package jadecrawler.website

import scala.language.implicitConversions

import org.jsoup.nodes.Element
import org.jsoup.select.Elements

object JsoupUtils {

	implicit def jsoupElementsWrapper(elem: Element) = elem :: Nil

	implicit def jsoupElementsWrapper(elems: Elements) = for(i <- 0 until elems.size) yield elems.get(i)

}

class HttpBeautifyUtils {
	import scala.io.Source
	import org.mozilla.javascript.Context
	import org.mozilla.javascript.Scriptable

	val ctx = Context.enter

	val jsBeautyScop: Scriptable = {
		val scope = ctx.initStandardObjects()
		val scripts = Source.fromInputStream(classOf[HttpBeautifyUtils]
			.getResourceAsStream("/js/beautifyjs/beautify.js")).mkString
		ctx.evaluateString(scope, scripts, null, 0, null)
		scope
	}

	val cssBeautyScop: Scriptable = {
		val scope = ctx.initStandardObjects()
		val scripts = Source.fromInputStream(classOf[HttpBeautifyUtils]
			.getResourceAsStream("/js/beautifyjs/beautify-css.js")).mkString
		ctx.evaluateString(scope, scripts, null, 0, null)
		scope
	}

	val htmlBeautyScop: Scriptable = {
		val scope = ctx.initStandardObjects()
		val scripts = Source.fromInputStream(classOf[HttpBeautifyUtils]
			.getResourceAsStream("/js/beautifyjs/beautify-html.js")).mkString
		ctx.evaluateString(scope, scripts, null, 0, null)
		scope
	}

	def formatJs(str: String) = {
		val code = str.replaceAll("""\\""","""\\\\""").replaceAll(""""""", """\\"""")
			.replaceAll("\n", "\\\\n")
		println(code)
		println("""js_beautify("%s")""" format code)
		ctx.evaluateString(jsBeautyScop, """js_beautify("%s")""" format code, null, 0, null)
	}


}
