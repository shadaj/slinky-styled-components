enablePlugins(ScalaJSBundlerPlugin)

organization := "me.shadaj"

name := "slinky-styled-components"

scalaVersion := "2.12.6"

libraryDependencies += "me.shadaj" %%% "slinky-web" % "0.5.0"

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.3" % Test

npmDependencies in Test += "react" -> "16.4.0"
npmDependencies in Test += "react-dom" -> "16.4.0"
npmDependencies in Test += "styled-components" -> "3.3.3"
jsDependencies += RuntimeDOM % Test

scalacOptions += "-P:scalajs:sjsDefinedByDefault"
scalacOptions += "-Ywarn-unused-import"
