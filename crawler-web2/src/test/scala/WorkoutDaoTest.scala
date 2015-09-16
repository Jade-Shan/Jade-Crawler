package jadecrawler.web2

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.junit.runner.RunWith

import jadeutils.common.Logging

object TestDaoComponent extends DaoContext with ServiceContext with Logging {

	val cfgFile = "workout.properties"
	logger.debug("----------- Loading props: {}", cfgFile)

	val envProps = new java.util.Properties()
	envProps.load(Thread.currentThread().getContextClassLoader()
		.getResourceAsStream(cfgFile))

}


@RunWith(classOf[JUnitRunner])
class AerobicRecordRecordDaoIntegrationTest extends FunSuite with Logging {

	val dao = TestDaoComponent.DaoCtx.aerobicRecordDao

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
		val ll = dao.findItems("user2", "aerobic9", 1442304500000L, 1442394930638L)
		//		assert(ll.size > 0)
		ll.foreach(logger info _.toString)
	}

}

@RunWith(classOf[JUnitRunner])
class StrengthRecordDaoIntegrationTest extends FunSuite with Logging {

	val dao = TestDaoComponent.DaoCtx.strengthRecordDao

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
		val ll = dao.findItems("user2", "strength8", 1442304500000L, 1442396317855L)
		//		assert(ll.size > 0)
		ll.foreach(logger info _.toString)
	}

}
