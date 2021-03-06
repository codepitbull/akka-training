package de.codepitbull.scala.akka.minimal

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.{MustMatchers, WordSpecLike}

class StringReplyActorTest extends TestKit(ActorSystem("testSystem"))
  // Using the ImplicitSender trait will automatically set `testActor` as the sender
  with ImplicitSender
  with WordSpecLike
  with MustMatchers {

  "A simple actor" must {
    "send back a result" in {
      val actorRef = TestActorRef[StringReplyActor]
      actorRef ! "akka"
      expectMsg("hallo akka")
    }

  }
}
