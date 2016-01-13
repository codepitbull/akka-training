package de.codepitbull.scala.akka.minimal

import akka.actor.Actor
import akka.dispatch.RequiresMessageQueue
import akka.dispatch.BoundedMessageQueueSemantics

class BoundedActor extends Actor
  with RequiresMessageQueue[BoundedMessageQueueSemantics] {
  var lastMsg: String = ""
  override def receive = {
    case msg: String => {
      lastMsg = msg
      println("received:" + lastMsg)
      sender ! "hallo " + msg
    }
  }
}

