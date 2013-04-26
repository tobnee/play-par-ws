package cache

import akka.actor.{Props, ActorRef, Actor}
import akka.event.Logging

class CreatorActor extends Actor {
  import context._
  val log = Logging(system, this)
  log.info("new creator actor")

  val map = collection.mutable.HashMap[String, ActorRef]()

  def receive = {
    case get:Get => {
      val res = map.get(get.url)
      val r = if(res.isDefined) {
        if(!res.get.isTerminated) res.get
        else newActor(get)
      } else newActor(get)
      r.forward(get)
    }
  }

  def newActor(get:Get) = {
    val ref = this.context.actorOf(Props[FirstCallStrategy])
    map += get.url -> ref
    ref
  }
}
