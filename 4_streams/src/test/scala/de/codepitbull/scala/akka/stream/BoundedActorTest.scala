package de.codepitbull.scala.akka.stream

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import com.typesafe.config.ConfigFactory
import org.scalatest.{MustMatchers, WordSpecLike}

class BoundedActorTest extends TestKit(ActorSystem("testSystem"))
  // Using the ImplicitSender trait will automatically set `testActor` as the sender
  with ImplicitSender
  with WordSpecLike
  with MustMatchers {

  "A simple actor" must {
    "send back a result" in {
      val actorRef = TestActorRef[BoundedActor]
      actorRef ! "akka"
      expectMsg("hallo akka")
    }

  }
}
