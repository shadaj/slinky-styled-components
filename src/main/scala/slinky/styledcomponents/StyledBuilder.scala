package slinky.styledcomponents

import slinky.core._
import slinky.readwrite.Writer

import scala.scalajs.js
import scala.scalajs.js.|
import js.JSConverters._

trait HasProps[A]
object HasProps {
  implicit def hasProps[A]: HasProps[A] = new HasProps[A] {}
  implicit def anyNoProps: HasProps[Any] = ???
}

final class StyledBuilder[P, TagType <: TagElement](private val innerObj: js.Object) extends AnyVal {
  def attrs(pairs: AttrPair[TagType]*): StyledBuilder[P, TagType] = {
    val dictionary = js.Dictionary[js.Any]()
    pairs.foreach(p => dictionary(p.name) = p.value)
    new StyledBuilder(innerObj.asInstanceOf[js.Dynamic].attrs(dictionary).asInstanceOf[js.Object])
  }

  def apply[NP <: P](c: InterpolatedCSS[NP])(implicit ev: HasProps[NP]): StyledExternalComponent[NP, TagType] = {
    new StyledExternalComponent[NP, TagType](innerObj.asInstanceOf[js.Function].call(
      js.undefined,
      c.parts.toJSArray +: c.interpolations: _*
    ).asInstanceOf[js.Object])
  }

  def apply(c: InterpolatedCSS[Any])(implicit ev: P =:= Any): StyledExternalComponentNoProps[TagType] = {
    new StyledExternalComponentNoProps[TagType](innerObj.asInstanceOf[js.Function].call(
      js.undefined,
      c.parts.toJSArray +: c.interpolations: _*
    ).asInstanceOf[js.Object])
  }
}

final class StyledExternalComponent[P, TagType <: TagElement](comp: js.Object) extends ExternalComponentWithAttributes[TagType]()(new Writer[P] {
  override def write(p: P): js.Object = js.Dynamic.literal(__ = p.asInstanceOf[js.Object])
}.asInstanceOf[ExternalPropsWriterProvider]) {
  override type Props = P
  override val component: String | js.Object = comp
}

final class StyledExternalComponentNoProps[TagType <: TagElement](comp: js.Object) extends ExternalComponentNoPropsWithAttributes[TagType] {
  override val component: String | js.Object = comp
}
