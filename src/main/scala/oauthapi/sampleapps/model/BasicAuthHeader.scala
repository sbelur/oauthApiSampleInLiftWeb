package oauthapi.sampleapps.model

import org.apache.commons.codec.binary.Base64
import org.apache.http.protocol.HTTP
import org.apache.http.util.CharArrayBuffer
import org.apache.http.util.EncodingUtils

object BasicAuthHeader {
  
  def apply(info:Tuple2[String,String]):String = {
	    val tmp = new StringBuilder()
		tmp.append(info._1)
		tmp.append(":")
		tmp.append(info._2)
		val base64password = Base64.encodeBase64(EncodingUtils.getBytes(tmp.toString(), HTTP.DEFAULT_PROTOCOL_CHARSET))

		val buffer = new CharArrayBuffer(32)
		buffer.append("Basic ")
		buffer.append(base64password, 0, base64password.length);
		buffer.toString
  }

}