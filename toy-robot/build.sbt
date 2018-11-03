organization := "com.robot"
scalaVersion := "2.12.6"
version      := "0.1.0-SNAPSHOT"

scalacOptions := Seq("-unchecked",
  "-deprecation",
  "-feature",
  "-language:higherKinds",
  "-Xfatal-warnings",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-unused",
  "-Ywarn-unused-import",
  "-Ypartial-unification",
  "-encoding",
  "utf8")

libraryDependencies += "org.typelevel" %% "cats-core" % "1.4.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "1.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test
