package cache

import akka.actor.Actor
import scala.concurrent.Future
import play.api.libs.ws.{WS, Response}
import akka.actor.PoisonPill;

import akka.event.Logging

class FirstCallStrategy extends Actor {
  import context._
  val log = Logging(system, this)
  log.info("new actor")

  implicit def ec = this.context.system

  var res:Future[Response] = _

  val selfRef = this.context.self

  def receive = {
    case e:Get => {
      println(e)
      if(res == null) {
        log.info("new future")
        res = WS.url(e.url).get()
        res.onComplete{ res =>
          log.info("kill")
          selfRef ! PoisonPill
        }
      } else {
        log.info("old future")
      }
      this.sender ! res
    }
  }
}
