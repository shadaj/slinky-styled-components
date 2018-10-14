package slinky.styledcomponents

case class InterpolatedCSS[-P](parts: Seq[String], interpolations: Seq[InterpolationPart[P]])
