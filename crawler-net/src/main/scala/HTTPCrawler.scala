package jadecrawler.net

import jadeutils.common.Logging


case class JadeHTTPResponse(cookies: List[Map[String, String]], content: Array[Byte])

object HTTPUtil extends Logging{

	import java.net.InetSocketAddress
	import java.net.URI

	import org.apache.http.client.methods.RequestBuilder
	import org.apache.http.client.protocol.HttpClientContext
	import org.apache.http.config.RegistryBuilder
	import org.apache.http.conn.socket.ConnectionSocketFactory
	import org.apache.http.conn.ssl.SSLContexts
	import org.apache.http.cookie.Cookie
	import org.apache.http.impl.client.CloseableHttpClient
	import org.apache.http.impl.client.HttpClients
	import org.apache.http.impl.client.BasicCookieStore
	import org.apache.http.impl.conn.PoolingHttpClientConnectionManager

	import jadeutils.base.FileOperater
	import jadeutils.net.FakeDnsResolver
	import jadeutils.net.ProxySSLSocketFactory
	import jadeutils.net.ProxySocketFactory

	import scala.collection.JavaConversions._
	import scala.language.implicitConversions

	def doGet(url: String, headers: Map[String, String]): JadeHTTPResponse = {
		doGet(url, headers, null, null, 0)
	}

	def doGet(url: String, headers: Map[String, String], 
		params: Map[String, String], proxyHost: String, 
		proxyPort: Int): JadeHTTPResponse =
	{
		val cookieStore = new BasicCookieStore();
		val client = newHttpClient(cookieStore, proxyHost, proxyPort)
		try {
			val builder = RequestBuilder.get().setUri(new URI(url))

			if (null != headers) {
				headers.foreach((e) => { builder.addHeader(e._1, e._2) })
			}
			if (null != params) {
				params.foreach((e) => { builder.addParameter(e._1, e._2) })
			}

			val resp = client.execute(builder.build())
			logDebug("HTTP get Status: {}", resp.getStatusLine)

			val entity = resp.getEntity()
			// if (null != entity) { entity.consumeContent() }

			val bytes = if (200 == resp.getStatusLine.getStatusCode) 
			FileOperater.readStream(entity.getContent())
			else new Array[Byte](0)

			val content = if (null != entity && null != entity.getContentEncoding &&
				"gzip" == entity.getContentEncoding.getValue) FileOperater.unGZip(bytes)
			else bytes

			val cs: java.util.List[Cookie] = cookieStore.getCookies()
			val cookies: List[Map[String, String]] = if (null != cs && cs.size > 0) {
					(for (c <- cs) yield (Map("name" -> c.getName, "value" -> c.getValue, 
						"domain" -> c.getDomain, "version" -> ("" + c.getVersion), 
						"path" -> c.getPath, "expiry" -> ("" + c.getExpiryDate.getTime)))
					).toList 
				} else { Nil }
			resp.close()
			new JadeHTTPResponse(cookies, content)
		} finally {
			client.close()
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
		val cookieStore = new BasicCookieStore();
		val client = newHttpClient(cookieStore, proxyHost, proxyPort)
		try {
			val builder = RequestBuilder.post().setUri(new URI(url))

			if (null != headers) {
				headers.foreach((e) => { builder.addHeader(e._1, e._2) })
			}
			if (null != params) {
				params.foreach((e) => { builder.addParameter(e._1, e._2) })
			}

			val resp = client.execute(builder.build())
			logDebug("HTTP get Status: {}", resp.getStatusLine)

			val entity = resp.getEntity()
			// if (null != entity) { entity.consumeContent() }

			val bytes = if (200 == resp.getStatusLine.getStatusCode) 
			FileOperater.readStream(entity.getContent())
			else new Array[Byte](0)

			val content = if (null != entity && null != entity.getContentEncoding &&
				"gzip" == entity.getContentEncoding.getValue) FileOperater.unGZip(bytes)
			else bytes

			val cs: java.util.List[Cookie] = cookieStore.getCookies()
			val cookies: List[Map[String, String]] = if (null != cs && cs.size > 0) {
					(for (c <- cs) yield (Map("name" -> c.getName, "value" -> c.getValue, 
						"domain" -> c.getDomain, "version" -> ("" + c.getVersion), 
						"path" -> c.getPath, "expiry" -> ("" + c.getExpiryDate.getTime)))
					).toList 
				} else { Nil }
			resp.close()
			new JadeHTTPResponse(cookies, content)
		} finally {
			client.close()
			new JadeHTTPResponse(Nil, new Array[Byte](0))
		}
	}

	def newHttpClient(cookieStore: BasicCookieStore, proxyHost: String, 
		proxyPort: Int): CloseableHttpClient = 
	{
		if (null != proxyHost && proxyPort > 0) {
			val reg = RegistryBuilder.create[ConnectionSocketFactory].register(
				"http", new ProxySocketFactory()).register(
				"https",new ProxySSLSocketFactory(SSLContexts.createSystemDefault())).build()
			var cm = new PoolingHttpClientConnectionManager(reg, new FakeDnsResolver())
			var client = HttpClients.custom().setConnectionManager(
				cm).setDefaultCookieStore(cookieStore).build()
			var context = HttpClientContext.create()
			context.setAttribute("socks.address", new InetSocketAddress(proxyHost, proxyPort))
			client
		} else {
			HttpClients.custom().setDefaultCookieStore(cookieStore).build()
		}
	}

	val firefoxParams = Map(
		"User-Agent" -> "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0", 
		"Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", 
	"Accept-Language" -> "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3", 
	"Accept-Encoding" -> "gzip,deflate", "Connection" -> "keep-alive")
}
