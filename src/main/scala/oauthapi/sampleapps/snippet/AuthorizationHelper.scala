package oauthapi.sampleapps {
package snippet {

import _root_.scala.xml.{NodeSeq, Text}
import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.js._
import _root_.net.liftweb.http.js.JE.JsRaw
import _root_.java.util.Date
import oauthapi.sampleapps.lib._
import Helpers._
import net.liftweb._
import http._
import common._
import util.Helpers._
import js._
import JsCmds._
import JE._
import oauthapi.sampleapps.util._
import SessionVars._

   class AuthorizationHelper  extends DispatchSnippet  {
  
		 def dispatch = {
		 	case "authorize" => authorize
		 }
  
		
		def authorize(n:NodeSeq) = {
		  lazy val template = "https://%s-api.apigee.com/v1/providers/%s/authorize?smartkey=%s&app_callback=%s"
		  lazy val url = String.format(template,"oauthApiSampleInLift","twitter",SessionVars.smartKeyRef.is,"http://localhost:8080")
		  bind("auth",n,"link"->{
		     SHtml.a(()=> RedirectTo(url),Text("Authorize"))   
		  })
		}
	}

}
}
