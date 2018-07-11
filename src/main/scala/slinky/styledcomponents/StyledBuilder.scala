package slinky.styledcomponents

import slinky.core.{AttrPair, ExternalComponentWithAttributes, ExternalPropsWriterProvider, TagElement}
import slinky.readwrite.Writer

import scala.scalajs.js
import scala.scalajs.js.|
import js.JSConverters._

final class StyledBuilder[P, TagType <: TagElement](private val innerObj: js.Object) extends AnyVal {
  def attrs(pairs: AttrPair[TagType]*): StyledBuilder[P, TagType] = {
    val dictionary = js.Dictionary[js.Any]()
    pairs.foreach(p => dictionary(p.name) = p.value)
    new StyledBuilder(innerObj.asInstanceOf[js.Dynamic].attrs(dictionary).asInstanceOf[js.Object])
  }

  def apply(c: InterpolatedCSS[P]): ExternalComponentWithAttributes[TagType] {
    type Props = P
  } = {
    new ExternalComponentWithAttributes[TagType]()(new Writer[P] {
      override def write(p: P): js.Object = js.Dynamic.literal(__ = p.asInstanceOf[js.Object])
    }.asInstanceOf[ExternalPropsWriterProvider]) {
      override type Props = P
      override val component: String | js.Object = {
        innerObj.asInstanceOf[js.Function].call(
          js.undefined,
          c.parts.toJSArray +: c.interpolations: _*
        ).asInstanceOf[js.Object]
      }
    }
  }
}
