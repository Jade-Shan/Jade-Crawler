package jadecrawler.web2

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import jadeutils.common.Logging

// import java.util.Properties
// import jadeutils.common.EnvPropsComponent
// 
// object TestWorkDaoComonent extends EnvPropsComponent with Logging {
// 	import java.util.Properties
// 
// 	val envProps: Properties = new Properties()
// 	envProps.load(Thread.currentThread().getContextClassLoader()
// 		.getResourceAsStream("workout.properties"))
// 
// }


@RunWith(classOf[JUnitRunner])
class AerobicRecordRecordDaoIntegrationTest extends FunSuite with Logging {

	val dao = new AerobicRecordDao("mongo.local-vm", 27017)

	val recs = for (i <- 0 until 10) yield { 
		new AerobicRecord("user" + (if (i < 5) 0 else 2), "aerobic" + i, i, i, i, 
			System.currentTimeMillis)
	}

	test("Test-AeroRec-toString") {
		recs.foreach(logger info _.toString)
	}

	test("Test-AeroRec-Save") {
		recs.foreach(dao insert _)
	}

	test("Test-AeroRec-find") {
		val ll = dao.findItems("user2", "aerobic9", 1442304500000L, 1442336317855L)
//		assert(ll.size > 0)
		ll.foreach(logger info _.toString)
	}

}

@RunWith(classOf[JUnitRunner])
class StrengthRecordDaoIntegrationTest extends FunSuite with Logging {

	val dao = new StrengthRecordDao("mongo.local-vm", 27017)

	val recs = for (i <- 0 until 10) yield { 
		new StrengthRecord("user" + (if (i < 5) 0 else 2), "strength" + i, i, i, 
			System.currentTimeMillis)
	}

	test("Test-StnRec-toString") {
		recs.foreach(logger info _.toString)
	}

	test("Test-StnRec-Save") {
		recs.foreach(dao insert _)
	}

	test("Test-StnRec-find") {
		val ll = dao.findItems("user2", "strength8", 1442304500000L, 1442336317855L)
//		assert(ll.size > 0)
		ll.foreach(logger info _.toString)
	}

}
