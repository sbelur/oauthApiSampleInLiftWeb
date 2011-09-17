package oauthapi.sampleapps {
package model {
   
 
  class AppUserInput(val userName:String,val password:String,val fullName:String="") extends Input {
	  
	    def context:String = "create"
		override def httpVerb:String = context match {
	      	case "create" => "POST"
	      	case "find" => "GET"  
	    }
		override def target:String = context match {
	      case "create" => "https://api.apigee.com/v1/apps/oauthApiSampleInLift/users.json"
	      case "find" => "https://oauthApiSampleInLift-api.apigee.com/v1/smartkeys/me.json"  
	    }
		override def body:String = """{"userName":"""+userName+""", "fullName":"""+fullName+""","password":""" +password+"}"
		override def isProviderRequest:Boolean = false
	}
  
 
}

}