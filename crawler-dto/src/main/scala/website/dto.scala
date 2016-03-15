package jadecrawler.dto.website

import jadeutils.mongo._

/* ================================================================= */
/*                  Modles for iciba crawler                         */
/* ================================================================= */

case class IcibaS2Dto(s1: String, s2: String) {
	def this() = this(null, null)

	@MongoField var str1 = s1
	@MongoField var str2 = s2

	override def toString = "{%s, %s}".format(str1, str2)
}

case class IcibaS3Dto(s1: String, s2: String, s3: String) {
	def this() = this(null, null, null)

	@MongoField var str1 = s1
	@MongoField var str2 = s2
	@MongoField var str3 = s3

	override def toString = "{%s, %s, %s}".format(str1, str2, str3) 
}

case class Opposite(s: String, ws: java.util.List[String]) {
	def this() = this(null, null)

	@MongoField var str = s
	@MongoField(ElemType = classOf[String]) var words = ws

	override def toString = "{%s, %s}".format(str, 
		if (null == words) "{}" else words.toString)
}

case class IcibaHomoDto(s: String, dto: java.util.List[IcibaS2Dto]) {
	def this() = this(null, null)

	@MongoField var str = s
	@MongoField(ElemType = classOf[IcibaS2Dto]) var s2dto = dto

	override def toString = "{%s, %s}".format(str, 
		if (null == s2dto) "{}" else s2dto.toString)
}

@MongoDocument(databaseName = "crawler", collectionName = "iciba")
case class IcibaDto(w: String, prons: java.util.List[IcibaS3Dto], 
	exps: java.util.List[(IcibaS2Dto)], rlts: java.util.List[IcibaS2Dto],
	exmps: java.util.List[IcibaS3Dto], homs: java.util.List[IcibaHomoDto],
	samws: java.util.List[Opposite], oppws: java.util.List[Opposite],
	phws: java.util.List[IcibaS3Dto]) extends MongoModel
{
	def this() = this(null, null, null, null, null, null, null, null, null)

	@MongoField                                   var word           = w
	@MongoField(ElemType = classOf[IcibaS3Dto])   var pronunciations = prons
	@MongoField(ElemType = classOf[IcibaS2Dto])   var explantions    = exps
	@MongoField(ElemType = classOf[IcibaS2Dto])   var relatedWords   = rlts
	@MongoField(ElemType = classOf[IcibaS3Dto])   var examples       = exmps
	@MongoField(ElemType = classOf[IcibaHomoDto]) var homoionyms     = homs
	@MongoField(ElemType = classOf[Opposite])     var sameWrds       = samws
	@MongoField(ElemType = classOf[Opposite])     var oppsites        = oppws
	@MongoField(ElemType = classOf[IcibaS3Dto])   var phrases        = phws

	override def toString = ( "{IcibaDto: {word=%s, pronunciations=%s, " + 
		"explantions=%s, relatedWords=%s, examples=%s, homoionyms=%s, " + 
		"sameWrds=%s, oppsites=%s, phrases=%s}}").format(word
		,if (null != pronunciations) pronunciations.toString else ""
		,if (null != explantions   ) explantions.toString    else ""
		,if (null != relatedWords  ) relatedWords.toString   else ""
		,if (null != examples      ) examples.toString       else ""
		,if (null != homoionyms    ) homoionyms.toString     else ""
		,if (null != sameWrds      ) sameWrds.toString       else ""
		,if (null != oppsites      ) oppsites.toString       else ""
		,if (null != phrases       ) phrases.toString        else "")

}

case class NewWord(w: String, c: Int) {

	def this() = this(null, 1)

	@MongoField var word  = w
	@MongoField var count = c

	override def toString = "{NewWord: {word=%s, count=%d}}".format(word, count)
}

@MongoDocument(databaseName = "crawler", collectionName = "newwordbook")
case class NewWordBook(u: String, p: String, ws: java.util.List[NewWord])
	extends MongoModel
{

	def this() = this(null, null, null)

	@MongoField                              var username = u
	@MongoField                              var password = p
	@MongoField(ElemType = classOf[NewWord]) var words    = ws

	override def toString = 
		"{NewWordBook: {username=%s, password=%s, words=%s}}".format(
			username, password, if (null != words) words.toString else "")
}

/* ================================================================= */
/*                  Modles for YYets crawler                         */
/* ================================================================= */

class YyetsRecListDto(val id: String, val name: String) extends MongoModel {
	override def toString = "{YyetsRecListDto: {id=%s, name=%s}}".format(id, name)
}


class YyetsLink(@MongoField linkType: String, @MongoField link: String) {
	override def toString = "{%s, %s}".format(linkType, link)
}

@MongoDocument(databaseName = "crawler", collectionName = "yyets")
class YyetsRecInfoDto(@MongoField id: String, @MongoField name: String, 
	@MongoField season: String, @MongoField episode: String,
	@MongoField format: String, @MongoField filename: String, 
	@MongoField size: String, @MongoField links: java.util.List[YyetsLink]
) extends MongoModel 
{

	def getId = id
	def getName = name
	def getSeason = season
	def getEpisode = episode
	def getFormat = format
	def getFilename = filename
	def getSize = size
	def getLinks = links

	override def toString = ("{className=YyetsRecInfoDto id=%s, name=%s, " + 
		"season=%s, episode=%s, format=%s, filename=%s, size=%s, links=%s}}"
	).format( id, name, season, episode, format, filename, size, 
	if(null!=links) links.toString else "")
}

