lazy val root = (project in file(".")).enablePlugins(PlayScala)

logLevel in rollup := Level.Debug

scalaVersion := "2.11.6"

JsEngineKeys.engineType := JsEngineKeys.EngineType.Node

entry in rollup := (sourceDirectory in Assets).value / "js" / "main.js"
