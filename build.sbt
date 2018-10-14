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
    s"""def ${escaped}[P] = new StyledBuilder[P, html.${escaped}.tagType](styledDictionary("${t}"))"""
  }.mkString("\n")
  
  val svgTagsSource = allSVGTags.map { t =>
    val escaped = if (needsEscape.contains(t)) "`" + t + "`" else t
    s"""def ${escaped}[P] = new StyledBuilder[P, svg.${escaped}.tagType](styledDictionary("${t}"))"""
  }.mkString("\n")

  IO.write(
    outFile,
    s"""package slinky.styledcomponents
       |import scala.scalajs.js
       |import slinky.web.{html, svg}
       |object styled{
       |val styledDictionary = StyledComponents.asInstanceOf[js.Dictionary[js.Object]]
       |$htmlTagsSource
       |$svgTagsSource
       |}""".stripMargin
  )
  Seq(outFile)
}.taskValue
