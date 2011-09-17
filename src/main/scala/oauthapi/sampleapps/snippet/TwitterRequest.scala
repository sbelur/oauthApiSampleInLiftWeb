package oauthapi.sampleapps {
package snippet {

  
import scala.util.parsing.json.JSON._  
import _root_.scala.xml._

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
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




case class TwitterRequest extends DispatchSnippet{

   
  case class TextElement(content:String)
  
  case class TextElementList(coll:List[String])
  
  def dispatch = {
    case "setup" => setUp
  }
  
  def makeTwitterRequest():JsCmd = {
			      val request = new RequestInput
				  val resp = new HttpClient {
				    type InputType = RequestInput
				  } invoke (request)
				  
				  
				  val allText = XML.loadString(resp) \ "status" \ "text"
				  implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[String])))
				  val coll = TextElementList((for(n <- allText) yield n.text).toList )
				  val asJson = write(coll)
				  println("---------------")
				  println(asJson)
				  JsRaw("var res = "+asJson+"['coll'];render(res);")
  }
  
  def setUp(html:NodeSeq) = {
	  bind("field",html,
			  "hidden" -> SHtml.hidden(makeTwitterRequest)
	  )
  }
  
  
}
}

}
