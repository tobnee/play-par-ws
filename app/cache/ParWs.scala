package cache

import play.api.cache.Cache
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.ws.{Response, WS}
import ExecutionContext.Implicits.global
import play.api.Play.current
import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import play.api.libs.concurrent.Akka._


object ParWs {

  lazy val cacheActor: ActorRef = system.actorOf(Props[CreatorActor], name = "creatorActor")
  implicit val timeout = Timeout(5 seconds)

  def get(uri:String) = {
    val res = cacheActor ? Get(uri)
    res.mapTo[Future[Response]].flatMap(e => e)
  }

}
