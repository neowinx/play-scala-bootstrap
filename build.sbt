name := "play-scala-bootstrap"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
 "net.fwbrasil" %% "activate-play" % "1.4.1" exclude("org.scala-stm", "scala-stm_2.10.0"),
 "net.fwbrasil" %% "activate-jdbc-async" % "1.4.1" exclude("org.scala-stm", "scala-stm_2.10.0"),
 "net.fwbrasil" %% "activate-mongo-async" % "1.4.1" exclude("org.scala-stm", "scala-stm_2.10.0"),
 "mysql" % "mysql-connector-java" % "5.1.16"
)     

play.Project.playScalaSettings
