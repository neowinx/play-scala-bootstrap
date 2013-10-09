import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play-scala-bootstrap"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "net.fwbrasil" %% "activate-play" % "1.4.1" exclude("org.scala-stm", "scala-stm_2.10.0"),
    "net.fwbrasil" %% "activate-jdbc-async" % "1.4.1" exclude("org.scala-stm", "scala-stm_2.10.0"),
    "net.fwbrasil" %% "activate-mongo-async" % "1.4.1" exclude("org.scala-stm", "scala-stm_2.10.0"),
    "mysql" % "mysql-connector-java" % "5.1.16"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
