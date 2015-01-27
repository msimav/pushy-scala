name := "pushy-scala"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.4"

libraryDependencies ++= Seq(
    "com.relayrides"    %    "pushy"          % "0.4.1",
    "com.typesafe.akka" %%   "akka-actor"     % "2.3.9",
    "org.scalatest"     %%   "scalatest"      % "2.2.1" % "test",
    "org.mockito"       %    "mockito-core"   % "1.9.5" % "test"
)
