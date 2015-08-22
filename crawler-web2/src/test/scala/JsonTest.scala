package jadeutils.common

import org.slf4j.LoggerFactory
import org.slf4j.Logger

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith


@RunWith(classOf[JUnitRunner])
class JsonTest extends FunSuite {
	import jadeutils.common.Json._

	val j1 = JObj ~ ("a", "1") ~ ("b", true) ~ ("c", 8) ~ ("d", 2.2) ~ ("e", 6553599999L)
	val j2 = JObj ~ ("aa", "1") ~ ("bb", true) ~ ("cc", 8) ~ ("dd", 2.2) ~ ("ee", 6553599999L)
	val j3 = JObj ~ ("aaa", "1") ~ ("bbb", true) ~ ("ccc", 8) ~ ("ddd", 2.2) ~ ("eee", 6553599999L)

	test("Test-Json-Obj") {
		assert(j1.toString == """{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}""")
		assert(j2.toString == """{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}""")
		assert(j3.toString == """{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}""")
	}

	val a1 = "a" :: "b" :: JArr
	val a2 = true :: false :: JArr
	val a3 = 1 :: 2 :: JArr
	val a4 = 6553599999L :: 6553600000L :: JArr
	val a5 = 1.1 :: 2.2 :: JArr
	val a6 = j1 :: j2 :: j3 :: JArr
	val a7 = a1 :: a2 :: a5 :: JArr

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
		assert(a4.getLong(0) == 6553600000L)
		intercept[IndexOutOfBoundsException] { a4.getJsonObject(3) }

		assert(a5.getDouble(0) = 1.1)
		assert(a5.getDouble(1) = 2.2)
		intercept[IndexOutOfBoundsException] { a5.getJsonObject(3) }

		assert(a6.toString == """[{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2},{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999},{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}]""")
		assert(a6.getJsonObject(0).toString == """{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2}""")
		assert(a6.getJsonObject(1).toString == """{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}""")
		assert(a6.getJsonObject(2).toString == """{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}""")
		intercept[IndexOutOfBoundsException] { a6.getJsonObject(3) }

		println(a7.toString)
		println(a7.getJsonArray(0))
		println(a7.getJsonArray(1))
		println(a7.getJsonArray(2))
		intercept[IndexOutOfBoundsException] { a7.getJsonArray(3) }
	}

	val j4 = JObj ~ ("a", "1") ~ ("b", j1) ~ ("c", j2)
	val j5 = JObj ~ ("a", "1") ~ ("b", j1) ~ ("c", a1)

	test("Test-Json-Obj-Obj-Arr") {
		assert(j4.toString == """{"a":"1","b":{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999},"c":{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999}}""")
		assert(j5.toString == """{"a":"1","b":{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999},"c":[{"aaa":"1","ccc":8,"bbb":true,"eee":6553599999,"ddd":2.2},{"aa":"1","bb":true,"cc":8,"dd":2.2,"ee":6553599999},{"a":"1","b":true,"c":8,"d":2.2,"e":6553599999}]}""")
	}

	test("Test-Json-getBasicValue") {
		assert(j1.getString("a") == "1")
		assert(j1.getBoolean("b") == true)
		assert(j1.getInt("c") == 8)
		assert(j1.getDouble("d") == 2.2)
		assert(j1.getLong("e") == 6553599999L)
	}


}

object JsonTest { 
	lazy val logger = LoggerFactory.getLogger(this.getClass)

	def getLoggerByName(name: String) = LoggerFactory.getLogger(name)
}

