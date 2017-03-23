sbt-rollup
===

[![Build Status](https://travis-ci.org/SwanAudio/sbt-rollup.svg?branch=master)](https://travis-ci.org/SwanAudio/sbt-rollup)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2058dfa8b93f4792bc8e69584c3b04dc)](https://www.codacy.com/app/lykathia/sbt-rollup?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SwanAudio/sbt-rollup&amp;utm_campaign=Badge_Grade)

This plugin provides support for running [rollup] in a [sbt-web] workflow.


Quickstart
---

Enable the plugin in your `plugins.sbt` file:

```scala
addSbtPlugin("com.swanaudio.sbt" % "sbt-rollup" % "1.0.0")
```

Define the entry point of your javascript module in your projects `build.sbt`:

```scala
entry in rollup := (sourceDirectory in Assets).value / "javascripts" / "main.js"
```


Add a base `rollup.config.js` to the project root:

```javascript
export default {
  format: 'umd',
  plugins: [ ]
};
```

Settings
---

Option | Description
-------|------------
entry  | Entry point for application bundle.
output | Filename for the output file.
config | Location of the rollup configuration file.


[sbt-web]: https://github.com/sbt/sbt-web
[rollup]: https://github.com/rollup/rollup
