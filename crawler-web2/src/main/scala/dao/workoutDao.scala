package jadecrawler.web2

import jadeutils.mongo.MongoServer
import jadeutils.mongo.BaseMongoDao

class StrengthRecordDao(serverList: java.util.List[MongoServer]) extends BaseMongoDao[StrengthRecord](serverList)
class AerobicRecordDao(serverList: java.util.List[MongoServer]) extends BaseMongoDao[AerobicRecord](serverList)
// val dao = new YyetsDao(new MongoServer("www.jade-dungeon.net", 27017) :: Nil)
