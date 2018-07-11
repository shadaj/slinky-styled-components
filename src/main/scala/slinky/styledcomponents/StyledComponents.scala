package slinky.styledcomponents

import slinky.core.TagElement

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

@JSImport("styled-components", JSImport.Namespace)
@js.native
object StyledComponentsNamespace extends js.Object {
  val css: js.Function = js.native
  val keyframes: js.Function = js.native
  val injectGlobal: js.Function = js.native
}
