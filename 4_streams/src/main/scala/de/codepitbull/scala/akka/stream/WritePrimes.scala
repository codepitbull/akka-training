package de.codepitbull.scala.akka.stream

import java.io.File
import java.util.concurrent.ThreadLocalRandom

import akka.actor.ActorSystem
import akka.stream.{ClosedShape, ActorMaterializer}
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.util.{Failure, Success}

object WritePrimes {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("Sys")
    import system.dispatcher

    implicit val materializer = ActorMaterializer()

    val maxRandomNumberSize = 1000000

    val primeSource: Source[Int, Unit] =
      Source(() => Iterator.continually(ThreadLocalRandom.current().nextInt(maxRandomNumberSize)))
          .filter(isPrime(_))
          .filter(prime => isPrime(prime + 2))

    import akka.stream.io.Implicits._
    val fileSink = Sink.synchronousFile(new File("target/primes.txt"))
    val slowSink = Flow[Int]
      .map(i => { Thread.sleep(1000); ByteString(i.toString) })
      .toMat(fileSink)((_, bytesWritten) => bytesWritten)

    val consoleSink = Sink.foreach[Int](println)

    val graph = FlowGraph.create(slowSink, consoleSink)((slow, _) => slow) { implicit builder =>
      (slow, console) =>
        import FlowGraph.Implicits._
        val broadcast = builder.add(Broadcast[Int](2)) // the splitter - like a Unix tee
        primeSource ~> broadcast ~> slow // connect primes to splitter, and one side to file
        broadcast ~> console // connect other side of splitter to console
        ClosedShape
    }
    val materialized = RunnableGraph.fromGraph(graph).run()

    // ensure the output file is closed and the system shutdown upon completion
    materialized.onComplete {
      case Success(_) =>
        system.shutdown()
      case Failure(e) =>
        println(s"Failure: ${e.getMessage}")
        system.shutdown()
    }


    def isPrime(n: Int): Boolean = {
      if (n <= 1) false
      else if (n == 2) true
      else !(2 to (n - 1)).exists(x => n % x == 0)
    }
  }
}
