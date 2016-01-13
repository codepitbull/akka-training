package de.codepitbull.scala.akka.minimal

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Created by jmader on 22.12.15.
  */
class StringReplyActor extends Actor{
  var lastMsg: String = ""
  override def receive = {
    case msg: String => {
      lastMsg = msg
      println("received:" + lastMsg)
      sender ! "hallo " + msg
    }
  }
}
