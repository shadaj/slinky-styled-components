val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.33")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

{
  if (scalaJSVersion.startsWith("0.6.")) addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler-sjs06" % "0.19.0")
  else addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.20.0")
}

libraryDependencies ++= {
  if (scalaJSVersion.startsWith("0.6.")) Nil
  else Seq("org.scala-js" %% "scalajs-env-jsdom-nodejs" % "1.1.0")
}

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.2")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.3")

addSbtPlugin("com.dwijnand" % "sbt-dynver" % "3.1.0")
