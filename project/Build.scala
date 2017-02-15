import sbt.Keys._
import sbt._

object ApplicationBuild extends Build {

  val appName = "test-project"
  val version = "0.0.1"

  lazy val offers = Project("offers", base = file("offers"))

  val main = Project(id = "test-project", base = file(".")) aggregate(offers)
}