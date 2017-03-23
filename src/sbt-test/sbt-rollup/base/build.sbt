lazy val root = (project in file(".")).enablePlugins(SbtWeb)

entry in rollup := (sourceDirectory in Assets).value / "main.js"
