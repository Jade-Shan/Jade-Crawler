package jadecrawler.web2

import jadeutils.mongo.MongoServer
import jadeutils.mongo.BaseMongoDao

class AerobicRecordDao(serverList: java.util.List[MongoServer]) extends BaseMongoDao[AerobicRecord](serverList)
class StrengthRecordDao(serverList: java.util.List[MongoServer]) extends BaseMongoDao[StrengthRecord](serverList)
