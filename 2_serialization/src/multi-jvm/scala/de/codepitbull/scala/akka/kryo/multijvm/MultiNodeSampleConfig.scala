package de.codepitbull.scala.akka.kryo.multijvm

import akka.remote.testkit.MultiNodeConfig
import com.typesafe.config.ConfigFactory

object MultiNodeSampleConfig extends MultiNodeConfig {
  val node1 = role("node1")
  val node2 = role("node2")
  testTransport(on = true)
  commonConfig(ConfigFactory.parseResources("application.conf"))
}
