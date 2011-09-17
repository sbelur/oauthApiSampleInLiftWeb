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


import oauthapi.sampleapps.model._
import oauthapi.sampleapps.util._
import SessionVars._
import scala.util.parsing.json.JSON._

case class ApplicationUser extends DispatchSnippet{

   
  
  var name:String = ""
  var fullName:String = ""  
  var pwd:String = ""
  
  def dispatch = {
    case "register" => signUp
    case "signin" => logIn
    case "loggedInCheck" => isLoggedIn
  }
  
  def isLoggedIn = 
    "#loginCheck *" #> {
          val skey = SessionVars.smartKeyRef.is
          if(skey != ""){
        	 Text("Click the link below to complete the Authorization process") 
          }
          else{
        	 Text("Please login before continuing")  
          }
      }
   
  
  private def loadUser():JsCmd = {
    val user = new  AppUserInput(name,pwd){
      override  def context = "find"
      override  def auth = (name,pwd)   
    }
    invokeUserResource(user)
  }
  
  private def createUser():JsCmd = {
    val user = new  AppUserInput(name,pwd,fullName){
      override def context = "create"
    }
    invokeUserResource(user)
  }
  
  private def invokeUserResource(user:AppUserInput):JsCmd = {
    val client = new HttpClient {
       type InputType = AppUserInput
       
    }
    val resp = client.invoke(user)
    val jsonified:Option[Any] = parseFull(resp)
    println(jsonified)
    jsonified match {
      case Some(x) => {
          val respAttrs = x.asInstanceOf[Map[String,String]]
    	  SessionVars.smartKeyRef(respAttrs get("smartKey") getOrElse "")
    	  var skeyValue = SessionVars.smartKeyRef.is
    	  if( skeyValue != "" && skeyValue != null && skeyValue.length > 0){
    	      if(user.context == "create"){
    	    	  S.notice("Congrats - you have registered")
    	      }
    	       else{
    	          S.notice("You have signed in to the application")
    	       }
    	  }
    	  else{
    	      SessionVars.smartKeyRef("")
    	      S.error(respAttrs get("message") get)
    	  }
      }
      case _ => {
    	  SessionVars.smartKeyRef("") 
    	  S.error("Oops - something when wrong - could not register - please try later")
      }
      Noop
    }
  }
  
  def signUp(html:NodeSeq) = {
	  bind("field",html,
			  "userName"->SHtml.text(name,name = _),
			  "fullName"->SHtml.text(fullName,fullName = _),
			  "userPwd"->SHtml.password(pwd,pwd = _) ,
			  "submit" -> (SHtml.hidden(createUser) ++ <input type="submit" value="Register"/>)
			  )
			  			  
  }
  
  def logIn(html:NodeSeq) = {
	  bind("field",html,
			  "userName"->SHtml.text(name,name = _),
			  "userPwd"->SHtml.password(pwd,pwd = _) ,
			  "submit" -> (SHtml.hidden(loadUser) ++ <input type="submit" value="SignIn"/>)
			  )
			  			  
  }
  
}

}
}
