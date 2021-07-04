name := "akka-quickstart-scala"

version := "1.0"

scalaVersion := "2.13.1"

lazy val akkaVersion = "2.6.15"
val mySqlVersion           = "5.1.34"
val slickVersion           = "3.1.0"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.15",
  "com.typesafe.akka" % "akka-stream-typed_2.13" % "2.6.15",
  "com.typesafe.akka" % "akka-http_2.13" % "10.2.4",
  "com.typesafe.akka" % "akka-http-spray-json_2.13" % "10.2.4",
  "ch.megard" % "akka-http-cors_2.13" % "1.1.1",
  "mysql" % "mysql-connector-java" % mySqlVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
  "org.scalatest" %% "scalatest" % "3.1.0" % Test
)
