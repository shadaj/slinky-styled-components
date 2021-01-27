enablePlugins(ScalaJSBundlerPlugin)

organization := "me.shadaj"

name := "slinky-styled-components"

val scala212 = "2.12.13"
val scala213 = "2.13.4"

scalaVersion := scala213
crossScalaVersions := Seq(scala212, scala213)

libraryDependencies += "me.shadaj" %%% "slinky-web" % "0.6.5"

libraryDependencies += "org.scalatest" %%% "scalatest" % "3.1.4" % Test

npmDependencies in Test += "react" -> "16.8.4"
npmDependencies in Test += "react-dom" -> "16.8.4"
npmDependencies in Test += "styled-components" -> "4.2.0"

requireJsDomEnv in Test := true

scalacOptions ++= {
  if (scalaJSVersion.startsWith("0.6.")) Seq("-P:scalajs:sjsDefinedByDefault")
  else Nil
}

scalacOptions ++= {
  if (scalaVersion.value == scala213) Seq("-Ymacro-annotations")
  else Nil
}

libraryDependencies ++= {
  if (scalaVersion.value == scala213) Seq.empty
  else Seq(compilerPlugin(("org.scalamacros" % "paradise" % "2.1.1").cross(CrossVersion.full)))
}

// Source Generation -----------------------------
// from styled-components/src/utils/domElements.js
val allHTMLTags = List(
  "a",
  "abbr",
  "address",
  "area",
  "article",
  "aside",
  "audio",
  "b",
  "base",
  "bdi",
  "bdo",
  "big",
  "blockquote",
  "body",
  "br",
  "button",
  "canvas",
  "caption",
  "cite",
  "code",
  "col",
  "colgroup",
  "data",
  "datalist",
  "dd",
  "del",
  "details",
  "dfn",
  "dialog",
  "div",
  "dl",
  "dt",
  "em",
  "embed",
  "fieldset",
  "figcaption",
  "figure",
  "footer",
  "form",
  "h1",
  "h2",
  "h3",
  "h4",
  "h5",
  "h6",
  "head",
  "header",
  // "hgroup", not supported in Slinky yet
  "hr",
  // "html", not supported in Slinky yet
  "i",
  "iframe",
  "img",
  "input",
  "ins",
  "kbd",
  "keygen",
  "label",
  "legend",
  "li",
  "link",
  "main",
  "map",
  "mark",
  // "marquee", not supported in Slinky yet
  "menu",
  "menuitem",
  "meta",
  "meter",
  "nav",
  "noscript",
  "object",
  "ol",
  "optgroup",
  "option",
  "output",
  "p",
  "param",
  "picture",
  "pre",
  "progress",
  "q",
  "rp",
  "rt",
  "ruby",
  "s",
  "samp",
  "script",
  "section",
  "select",
  "small",
  "source",
  "span",
  "strong",
  "style",
  "sub",
  "summary",
  "sup",
  "table",
  "tbody",
  "td",
  "textarea",
  "tfoot",
  "th",
  "thead",
  "time",
  "title",
  "tr",
  "track",
  "u",
  "ul",
  "var",
  "video",
  "wbr"
)

val allSVGTags = List(
  "circle",
  "clipPath",
  "defs",
  "ellipse",
  "foreignObject",
  "g",
  "image",
  "line",
  "linearGradient",
  "mask",
  "path",
  "pattern",
  "polygon",
  "polyline",
  "radialGradient",
  "rect",
  "stop",
  // "svg", not supported in Slinky yet
  "text",
  "tspan"
)

val needsEscape = Set("object", "var")

sourceGenerators in Compile += Def.task {
  val outFile = (sourceManaged in Compile).value / "styled.scala"
  
  val htmlTagsSource = allHTMLTags.map { t =>
    val escaped = if (needsEscape.contains(t)) "`" + t + "`" else t
    s"""def ${escaped} = new StyledBuilder[Any, html.${escaped}.tagType](styledDictionary("${t}"))"""
  }.mkString("\n")
  
  val svgTagsSource = allSVGTags.map { t =>
    val escaped = if (needsEscape.contains(t)) "`" + t + "`" else t
    s"""def ${escaped} = new StyledBuilder[Any, svg.${escaped}.tagType](styledDictionary("${t}"))"""
  }.mkString("\n")

  IO.write(
    outFile,
    s"""package slinky.styledcomponents
       |import scala.scalajs.js
       |import slinky.web.{html, svg}
       |object styled extends StyledCore {
       |val styledDictionary = StyledComponents.asInstanceOf[js.Dictionary[js.Object]]
       |$htmlTagsSource
       |$svgTagsSource
       |}""".stripMargin
  )
  Seq(outFile)
}.taskValue
