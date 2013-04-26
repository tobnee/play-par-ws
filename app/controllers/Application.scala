package controllers

import _root_.cache.ParWs
import play.api.mvc._
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def search(term: String) = Action {
    Async {
      val res = ParWs.get(s"https://www.google.de/search?q=$term")
        .map(e => e.body)
      res.map(content => Ok(content).as("text/html"))
    }
  }

}