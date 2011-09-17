package oauthapi.sampleapps {
package rest {

  
import scala.util.parsing.json.JSON._  
import _root_.scala.xml._

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.rest._
import _root_.net.liftweb.http.js._
import _root_.net.liftweb.http.js.JE.JsRaw
import _root_.java.util.Date

import Helpers._
import net.liftweb._
import http._
import common._
import util.Helpers._
import js._
import JsCmds._
import JE._
import net.liftweb.json.Serialization.write
import net.liftweb.json._

import oauthapi.sampleapps.lib._
import oauthapi.sampleapps.model._
import oauthapi.sampleapps.util._
import SessionVars._




object TwitterRequest extends RestHelper{

   
  case class TextElement(content:String)
  
  case class TextElementList(coll:List[String])
  
  
  
  serve {
    
    case Req("providers"::"twitter"::Nil,_,GetRequest) =>
       () => {
                  println("\n\n---------------\n\n")
    	   			println("Rest helper invoked....")
    	   	      val request = new RequestInput
				  val resp = new HttpClient {
				    type InputType = RequestInput
				  } invoke (request)
				  val allText = XML.loadString(resp) \ "status" 
				  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[String])))
				  val coll = TextElementList((for(n <- allText) yield (n \ "text" text) + "..." + (n \ "user" \ "screen_name" text) + "").toList )
				  val asJson = write(coll)
				  println("---------------")
				  println(asJson)
				  Box(JsonResponse(JsRaw(asJson.toString),Nil,Nil,200))
       }
    
  }

  
  

}

}


}