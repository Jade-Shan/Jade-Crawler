package jadeutils.common

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class JsonTest extends FunSuite {
	import jadeutils.common.JsonObject.JObj
	import jadeutils.common.JsonArray.JArr

	val j1 = JObj ~ ("a", "1") ~ ("b", true) ~ ("c", 8) ~ ("d", 2.2) ~ ("e", 6553599999L)
	val j2 = JObj ~ ("aa", "1") ~ ("bb", true) ~ ("cc", 8) ~ ("dd", 2.2) ~ ("ee", 6553599999L)
	val j3 = JObj ~ ("aaa", "1") ~ ("bbb", true) ~ ("ccc", 8) ~ ("ddd", 2.2) ~ ("eee", 6553599999L)

	test("Test-Json-Obj") {
		assert(j1.toString == """{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}""")
		assert(j1.getString("a") == "1")
		assert(j1.getBoolean("b") == true)
		assert(j1.getInt("c") == 8)
		assert(j1.getDouble("d") == 2.2)
		assert(j1.getLong("e") == 6553599999L)

		assert(j2.toString == """{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}""")
		assert(j3.toString == """{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}""")
	}

	val a1 = JArr ~~ "a" ~~ "b"
	val a2 = JArr ~~ true ~~ false
	val a3 = JArr ~~ 1 ~~ 2
	val a4 = JArr ~~ 6553599999L ~~ 6553600000L
	val a5 = JArr ~~ 1.1 ~~ 2.2
	val a6 = JArr ~~ j1 ~~ j2 ~~ j3
	val a7 = JArr ~~ a1 ~~ a2 ~~ a5

	test("Test-Json-Arr") {
		assert(a1.getString(0) == "a")
		assert(a1.getString(1) == "b")
		intercept[IndexOutOfBoundsException] { a1.getJsonObject(3) }

		assert(a2.getBoolean(0) == true)
		assert(a2.getBoolean(1) == false)
		intercept[IndexOutOfBoundsException] { a2.getJsonObject(3) }

		assert(a3.getInt(0) == 1)
		assert(a3.getInt(1) == 2)
		intercept[IndexOutOfBoundsException] { a3.getJsonObject(3) }

		assert(a4.getLong(0) == 6553599999L)
		assert(a4.getLong(1) == 6553600000L)
		intercept[IndexOutOfBoundsException] { a4.getJsonObject(3) }

		assert(a5.getDouble(0) == 1.1)
		assert(a5.getDouble(1) == 2.2)
		intercept[IndexOutOfBoundsException] { a5.getJsonObject(3) }

		assert(a6.getJsonObject(0).toString == """{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}""")
		assert(a6.getJsonObject(1).toString == """{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}""")
		assert(a6.getJsonObject(2).toString == """{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}""")
		intercept[IndexOutOfBoundsException] { a6.getJsonObject(3) }

		assert(a7.getJsonArray(0).toString == """["a","b"]""")
		assert(a7.getJsonArray(1).toString == """[true,false]""")
		assert(a7.getJsonArray(2).toString == """[1.1,2.2]""")
		intercept[IndexOutOfBoundsException] { a7.getJsonArray(3) }
	}

	val j4 = JObj ~ ("a", "1") ~ ("b", j1) ~ ("c", j2)
	val j5 = JObj ~ ("a", "1") ~ ("b", a1) ~ ("c", a6) ~ ("d", a7)
	val j6 = JObj ~ ("a", JArr ~~ 1 ~~ 2 ~~ 3) ~ 
									("b", JArr ~~ (JObj ~ ("a", "1") ~ ("b", true)) ~~ 
																(JObj ~ ("c", "2") ~ ("d", true)) ~~ 
																(JObj ~ ("e", "3") ~ ("f", true))) ~
									("c", JArr ~~ (JArr ~~ "a"  ~~ "b"  ) ~~ 
																(JArr ~~ true ~~ false) ~~ 
																(JArr ~~ 1    ~~ 2    ))

	test("Test-Json-Obj-Obj-Arr") {
		assert(j4.getJsonObject("b").toString == """{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}""")
		assert(j4.getJsonObject("c").toString == """{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}""")

		assert(j5.getJsonArray("b").toString == """["a","b"]""")
		assert(j5.getJsonArray("c").toString == """[{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999},{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999},{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}]""")
		assert(j5.getJsonArray("d").toString == """[["a","b"],[true,false],[1.1,2.2]]""")

		assert(j6.getJsonArray("a").toString == """[1,2,3]""")
		assert(j6.getJsonArray("b").toString == """[{"a":"1","b":true},{"c":"2","d":true},{"e":"3","f":true}]""")
		assert(j6.getJsonArray("c").toString == """[["a","b"],[true,false],[1,2]]""")
	}

}

object JsonTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

