package oauthapi.sampleapps {
package model {


trait Input {
	def httpVerb:String
	def target:String
	def body:String
	def auth:Tuple2[String,String] = ("","")
	def isProviderRequest:Boolean
}

}
}