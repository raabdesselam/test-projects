import sbt.Keys._
import sbt._

object ApplicationBuild extends Build {

  val appName = "test-project"
  val version = "0.0.1"

  lazy val offers = Project("offers", base = file("offers"))

  lazy val scalaWithCats = Project("scalawithcats", base = file("scalawithcats"))

  val main = Project(id = "test-project", base = file(".")) aggregate(offers,scalaWithCats)
}