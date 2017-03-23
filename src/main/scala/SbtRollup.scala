package com.swanaudio.sbt
package rollup

import sbt._, Keys._
import com.typesafe.sbt.web.Import.WebKeys
import com.typesafe.sbt.jse.SbtJsTask
import spray.json.JsValue

import scala.concurrent.duration._

object SbtRollup extends AutoPlugin {

  override def requires = SbtJsTask
  override def trigger = AllRequirements

  object autoImport {
    lazy val rollup = TaskKey[Seq[File]]("rollup", "Perform module bundling.")
    lazy val entry = SettingKey[File]("rollup-entry", "Entry point for application bundle.")
    lazy val output = SettingKey[String]("rollup-output", "Name of output file produced.")
    lazy val config = SettingKey[File]("rollup-config", "The location of the rollup configuration file.")
  }

  import autoImport._
  import com.typesafe.sbt.web.SbtWeb.autoImport._
  import com.typesafe.sbt.jse.JsEngineImport.JsEngineKeys._

  override lazy val projectSettings: Seq[Setting[_]] =
    super.projectSettings ++ sbtRollupProjectSettings

  lazy val sbtRollupProjectSettings: Seq[Def.Setting[_]] = Seq(
    config := baseDirectory.value / "rollup.config.js",
    output := "bundle.js",
    rollup in Assets := runRollup.dependsOn(WebKeys.webJarsNodeModules in Plugin).value,
    rollup in TestAssets := runRollup.dependsOn(WebKeys.webJarsNodeModules in Plugin).value,
    resourceManaged in rollup in Assets := WebKeys.webTarget.value / rollup.key.label / "main",
    resourceManaged in rollup in TestAssets := WebKeys.webTarget.value / rollup.key.label / "test",
    rollup := (rollup in Assets).value
  ) ++ inConfig(Assets)(unscopedRollupSettings) ++ inConfig(TestAssets)(unscopedRollupSettings)

  private[this] lazy val unscopedRollupSettings: Seq[Def.Setting[_]] = Seq(
    resourceGenerators += rollup.taskValue,
    managedResourceDirectories += (resourceManaged in rollup).value
  ) ++ inTask(rollup)(Seq(
    sourceDirectories := unmanagedSourceDirectories.value ++ managedSourceDirectories.value,
    sources := unmanagedSources.value ++ managedSources.value
  ))

  private[this] def runRollup: Def.Initialize[Task[Seq[File]]] = Def.task {
    val inputFile = (entry in rollup).value
    val outputFile = (resourceManaged in rollup).value / (output in rollup).value
    val arguments: Seq[String] = Seq(
      "-c", config.value.getPath,
      "-i", inputFile.getPath,
      "-o", outputFile.getPath
    )

    SbtJsTask.executeJs(
      state.value,
      (engineType in Plugin).value,
      (command in Plugin).value,
      Seq((WebKeys.webJarsNodeModulesDirectory in Plugin).value).map(_.getPath),
      (WebKeys.webJarsNodeModulesDirectory in Plugin).value / "rollup" / "bin" / "rollup",
      arguments,
      1.minute
    )

    Seq(outputFile)
  }

}
