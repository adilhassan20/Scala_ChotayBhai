name := "AkkaRestSlickApi"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= {
  val akkaStreamVersion = "2.0-M2"
  val akkaVersion = "2.3.12"
  val scalaTestVersion       = "2.2.4"
  val scalaMockVersion       = "3.2.2"
  val slickVersion           = "3.1.0"
  val mySqlVersion           = "5.1.34"
  val flywayVersion          = "3.2.1"

  Seq(
    "com.typesafe.akka" %% "akka-actor"                           % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-experimental"             % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-experimental"               % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-core-experimental"          % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental"    % akkaStreamVersion,
    "com.typesafe.akka" %% "akka-http-testkit-experimental"       % akkaStreamVersion,
    "mysql"              % "mysql-connector-java"                  % mySqlVersion,
    "org.scalatest" %% "scalatest" % "2.2.1" % "test",
    "com.typesafe.akka" %% "akka-http-testkit-experimental"% "1.0"
  )
}


resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
enablePlugins(DebianPlugin)

maintainer in Linux := "SoftTelecom"

packageDescription := "Demo"


