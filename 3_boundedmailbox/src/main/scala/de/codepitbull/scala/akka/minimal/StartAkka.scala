package de.codepitbull.scala.akka.minimal

import akka.pattern.ask
import akka.actor._
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.Await


/**
  * Starting a minimal actor system and doing stuff with it.
  */
object StartAkka extends App{
  implicit val timeout = Timeout(5 seconds)
  val sys = ActorSystem("HelloWorld")
  val testActor = sys.actorOf(Props[BoundedActor], name = "hello")
  val future = testActor ? "huhu"
  val result = Await.result(future, Duration("2 seconds")).asInstanceOf[String]
  println(result)

}
