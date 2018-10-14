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
object StyledComponents extends js.Function1[js.Any, js.Object] {
  override def apply(arg1: js.Any): js.Object = js.native
}

@JSImport("styled-components", JSImport.Namespace)
@js.native
object StyledComponentsNamespace extends js.Object {
  val css: js.Function = js.native
  val keyframes: js.Function = js.native
  val injectGlobal: js.Function = js.native
}
