logLevel := Level.Debug

lazy val root = Project("plugins", file(".")).dependsOn(plugin)

lazy val plugin = file("../").getCanonicalFile.toURI

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.10")
