package cache

import akka.actor.ActorRef

sealed trait CacheMsg
case class Get(url:String) extends CacheMsg
