package slinky.styledcomponents

import slinky.core.{Tag, TagElement}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
trait StyledComponentConstructor[P] extends js.Object {
  type TagType <: TagElement
}

@JSImport("styled-components", JSImport.Default)
@js.native
object StyledComponents extends js.Object {
  val button: js.Function = js.native
}
