package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.provider._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.net.liftweb.mapper.{DB, ConnectionManager, Schemifier, DefaultConnectionIdentifier, StandardDBVendor}
import _root_.java.sql.{Connection, DriverManager}
import _root_.oauthapi.sampleapps.model._
import _root_.oauthapi.sampleapps.rest._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    
    // where to search snippet
    LiftRules.addToPackages("oauthapi.sampleapps")
   
    LiftRules.snippetDispatch.append {
      case "ApplicationUser" => new oauthapi.sampleapps.snippet.ApplicationUser
      case "TwitterRequest" => new oauthapi.sampleapps.snippet.TwitterRequest
      case "Authorization" => new  oauthapi.sampleapps.snippet.AuthorizationHelper
    }
    
    
    LiftRules.dispatch.append(TwitterRequest)
    
    
    LiftRules.ajaxPostTimeout = 10000

    // Build SiteMap
    def sitemap() = SiteMap(
      Menu("Home") / "index",
      Menu("Register") / "register",
      Menu("Login") / "login",
      Menu("Authorize") / "authorize",
      Menu("Twitter Timelines") / "twitter",
      Menu("Author") / "Author"
    )

    LiftRules.setSiteMapFunc(() =>sitemap())

    /*
     * Show the spinny image when an Ajax call starts
     */
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    /*
     * Make the spinny image go away when it ends
     */
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(makeUtf8)

  }

  /**
   * Force the request to be UTF-8
   */
  private def makeUtf8(req: HTTPRequest) {
    req.setCharacterEncoding("UTF-8")
  }
}
