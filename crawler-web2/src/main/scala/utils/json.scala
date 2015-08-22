package jadeutils.common


import org.json.JSONObject
import org.json.JSONArray

// jobj
// asInstanceOf    get                     
// isInstanceOf    isNull          join            length         
// optInt          optJSONArray    optJSONObject   optLong         optString      
// toString        write     
//          
// opt             optBoolean      optDouble       
// put             remove          toJSONObject    
//
// getString       getBoolean      getInt        getDouble
// getLong         getJSONArray    getJSONObject   
//
//
//
// jarray length

class JsonObject(str: String) {
	var value: JSONObject = if (null == str) null else new JSONObject(str)

	def this() = this("{}")

	def ~(k: String, v: String) = { value.put(k, v); this }
	def ~(k: String, v: Boolean) = { value.put(k, v); this }
	def ~(k: String, v: Int) = { value.put(k, v); this }
	def ~(k: String, v: Double) = { value.put(k, v); this }
	def ~(k: String, v: Long) = { value.put(k, v); this }
	def ~(k: String, v: JsonObject) = { value.put(k, v.value); this }
	def ~(k: String, v: JsonArray) = { value.put(k, v.value); this }

	override def toString = value.toString
}
object JsonObject {}

class JsonArray(str: String) {
	var value: JSONArray = if (null == str) null else new JSONArray(str)

	def this() = this("[]")

	def ::(o: JsonObject) = { value.put(o.value); this }

	def apply(i: Int): Option[JsonObject] =
		if (i < value.length) {
			val o = new JsonObject(null)
			o.value = value.get(i).asInstanceOf[JSONObject]
			Some(o)
		} else None

	override def toString = value.toString
}

object Json { 
	def JObj = new JsonObject
	def JArr = new JsonArray 
}

