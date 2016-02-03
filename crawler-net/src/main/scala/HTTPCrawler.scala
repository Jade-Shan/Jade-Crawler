package jadecrawler.net


case class JadeHTTPResponse(cookies: List[Map[String, String]], content: Array[Byte])

object HTTPUtil {

//	import java.io.ByteArrayInputStream
//	import java.io.ByteArrayOutputStream
//	import java.io.IOException
//	import java.io.InputStream
		import java.net.InetSocketAddress
//	import java.util.zip.GZIPInputStream
//	import java.util.zip.GZIPOutputStream

	import org.apache.http.client.methods.HttpGet
//	import org.apache.http.client.ClientProtocolException
	import org.apache.http.client.entity.UrlEncodedFormEntity
//	import org.apache.http.client.methods.CloseableHttpResponse
	import org.apache.http.client.methods.HttpPost;
	import org.apache.http.client.protocol.HttpClientContext
//	import org.apache.http.conn.DnsResolver
	import org.apache.http.conn.socket.ConnectionSocketFactory
//	import org.apache.http.conn.socket.PlainConnectionSocketFactory
//	import org.apache.http.conn.ssl.SSLConnectionSocketFactory
	import org.apache.http.conn.ssl.SSLContexts
//	import org.apache.http.config.Registry
	import org.apache.http.config.RegistryBuilder
	import org.apache.http.cookie.Cookie
	import org.apache.http.HttpResponse
	import org.apache.http.HttpEntity
//	import org.apache.http.impl.client.BasicResponseHandler
	import org.apache.http.impl.client.CloseableHttpClient
//	import org.apache.http.impl.client.DefaultHttpClient
	import org.apache.http.impl.client.HttpClients
	import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
	import org.apache.http.message.BasicNameValuePair
//	import org.apache.http.NameValuePair
//	import org.apache.http.protocol.HttpContext
	import org.apache.http.util.EntityUtils



	import jadeutils.base.FileOperater
	import jadeutils.net.FakeDnsResolver
	import jadeutils.net.ProxySSLSocketFactory
	import jadeutils.net.ProxySocketFactory

	import scala.collection.JavaConversions._
	import scala.language.implicitConversions

	def doGet(url: String, headers: Map[String, String]): JadeHTTPResponse = 
		doGet(url, headers, null, 0)

	def doGet(url: String, headers: Map[String, String], proxyHost: String,
		proxyPort: Int): JadeHTTPResponse =
	{
		val client = new DefaultHttpClient()
		// val client = newHttpClient(proxyHost, proxyPort)
		try {
			val httpGet = new HttpGet(url)
			if (null != headers)
				headers.foreach((e) => { httpGet.setHeader(e._1, e._2) })

			val resp = client.execute(httpGet)
			println(resp.getStatusLine)

			val entity = resp.getEntity()
			// if (null != entity) { entity.consumeContent() }

			val bytes = if (200 == resp.getStatusLine.getStatusCode) 
			FileOperater.readStream(entity.getContent())
			else new Array[Byte](0)

			val content = if (null != entity && null != entity.getContentEncoding &&
				"gzip" == entity.getContentEncoding.getValue) 
			FileOperater unGZip bytes
			else bytes

			val cs: java.util.List[Cookie] = client.getCookieStore.getCookies
			val cookies: List[Map[String, String]] = if (null != cs && cs.size > 0) {
				(for (c <- cs) yield (Map("name" -> c.getName, "value" -> c.getValue, 
					"domain" -> c.getDomain, //"version" -> ("" + c.getVersion), 
				"path" -> c.getPath
				//, "expiry" -> ("" + c.getExpiryDate.getTime)
		))).toList } 
			else { Nil }
			new JadeHTTPResponse(cookies, content)
		} finally {
			client.getConnectionManager.shutdown
			new JadeHTTPResponse(Nil, new Array[Byte](0))
		}
	}

	def doPost(url: String, headers: Map[String, String], 
		params: List[(String, String)]): JadeHTTPResponse = 
	{ doPost(url, headers, params, null, 0) }

	def doPost(url: String, headers: Map[String, String], 
		params: List[(String, String)], proxyHost: String, proxyPort: Int): 
		JadeHTTPResponse =
	{
		val client = new DefaultHttpClient()
		try {
			val httpPost = new HttpPost(url)
			if (null != headers)
				headers.foreach((e) => { httpPost.setHeader(e._1, e._2) })

			httpPost.setEntity(new UrlEncodedFormEntity(
				for(e <- params) yield new BasicNameValuePair(e._1, e._2)))
				val resp = client.execute(httpPost)
			println(resp.getStatusLine)

			val entity = resp.getEntity()
			// if (null != entity) { entity.consumeContent() }

			val bytes = if (200 == resp.getStatusLine.getStatusCode) 
			FileOperater.readStream(entity.getContent())
			else new Array[Byte](0)

			val content = if (null != entity && null != entity.getContentEncoding &&
				"gzip" == entity.getContentEncoding.getValue) 
			FileOperater unGZip bytes
			else bytes

			val cs: java.util.List[Cookie] = client.getCookieStore.getCookies
			val cookies: List[Map[String, String]] = if (null != cs && cs.size > 0) {
				(for (c <- cs) yield (Map("name" -> c.getName, "value" -> c.getValue, 
					"domain" -> c.getDomain, "path" -> c.getPath, "version" -> ("" + c.getVersion), 
					"expiry" -> (if (null != c.getExpiryDate) ("" + c.getExpiryDate.getTime) else "0")))).toList } 
			else { Nil }
			new JadeHTTPResponse(cookies, content)
		} finally {
			client.getConnectionManager.shutdown
			new JadeHTTPResponse(Nil, new Array[Byte](0))
		}
	}

	def newHttpClient(proxyHost: String, proxyPort: Int): CloseableHttpClient = {
		if (null != proxyHost && proxyPort > 0) {
			val reg = RegistryBuilder.create[ConnectionSocketFactory].register(
				"http", new ProxySocketFactory()).register(
				"https",new ProxySSLSocketFactory(
					SSLContexts.createSystemDefault())).build()
				var cm = new PoolingHttpClientConnectionManager(reg, new FakeDnsResolver())
				var client = HttpClients.custom().setConnectionManager(cm).build();
				var context = HttpClientContext.create();
					context.setAttribute("socks.address",
						new InetSocketAddress(proxyHost, proxyPort));
				client
		} else {
			HttpClients.createDefault()
		}
	}

	val firefoxParams = Map(
		"User-Agent" -> "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0", 
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", 
		"Accept-Language" -> "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3", 
		"Accept-Encoding" -> "gzip,deflate", "Connection" -> "keep-alive")
}
