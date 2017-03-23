sbtPlugin := true

scalaVersion := "2.10.6"

name := "sbt-rollup"
description := "Allows rollup to be used from within a SBT workflow"
organization := "com.swanaudio"
homepage := Some(url("https://github.com/swanaudio/sbt-rollup"))
licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

libraryDependencies ++= Seq(
  "org.webjars.npm" % "rollup" % "0.41.4"
)

/* Auto Plugins */
addSbtPlugin("com.typesafe.sbt" % "sbt-js-engine" % "1.1.4")

/* Scripted */
scriptedSettings
scriptedLaunchOpts := { scriptedLaunchOpts.value ++
  Seq("-Xmx1024M", "-Dplugin.version=" + version.value)
}
