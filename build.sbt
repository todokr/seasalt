name := "seasalt"

version := "0.1"

scalaVersion := "2.13.1"

lazy val core = (project in file("core"))
  .settings(
    name := "seasalt-core",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % "2.6.0-RC1",
      "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      "com.typesafe.akka" %% "akka-testkit" % "2.6.0-RC1" % Test,
      "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.6.0-RC1" % Test,

    )
  )

lazy val server = (project in file("server"))
  .settings(
    name := "seasalt-server",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % "2.6.0-RC1",
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
      "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime,
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    )
  )