package oauthapi.sampleapps {
package model {
   
import oauthapi.sampleapps.util._
import SessionVars._
 
  class RequestInput extends Input {
	  
	    
		override def httpVerb:String = "GET"
		override def target = "http://oauthApiSampleInLift-api.apigee.com/v1/twitter/1/statuses/home_timeline.xml?smartkey="+SessionVars.smartKeyRef.is
		override def body:String = ""
		override def isProviderRequest:Boolean = true
	}
  
 
}

}