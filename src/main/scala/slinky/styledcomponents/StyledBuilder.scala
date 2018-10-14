package slinky.styledcomponents

import slinky.core._
import slinky.readwrite.Writer

import scala.scalajs.js
import scala.scalajs.js.|
import js.JSConverters._
import scala.annotation.implicitNotFound

trait HasProps[A]
object HasProps {
  @implicitNotFound("Could not prove that the styled component takes no props. If you intend for the component to take props, you will need to explicitly call .apply[Props]")
  type NoProps[P] = P =:= Any

  implicit def hasProps[A]: HasProps[A] = new HasProps[A] {}
  implicit def anyNoProps: HasProps[Any] = ???
}

final class StyledBuilder[P, TagType <: TagElement](private val innerObj: js.Object, writer: Writer[P] = null) {
  def attrs(pairs: AttrPair[TagType]*): StyledBuilder[P, TagType] = {
    val dictionary = js.Dictionary[js.Any]()
    pairs.foreach(p => dictionary(p.name) = p.value)
    new StyledBuilder(innerObj.asInstanceOf[js.Dynamic].attrs(dictionary).asInstanceOf[js.Object])
  }

  def apply[NP <: P](c: InterpolatedCSS[NP])(implicit ev: HasProps[NP]): StyledExternalComponent[NP, TagType] = {
    new StyledExternalComponent[NP, TagType](innerObj.asInstanceOf[js.Function].call(
      js.undefined,
      c.parts.toJSArray +: c.interpolations: _*
    ).asInstanceOf[js.Object], if (writer == null) {
      p: NP => js.Dynamic.literal(__ = p.asInstanceOf[js.Object])
    } else writer.asInstanceOf[Writer[NP]])
  }

  def apply(c: InterpolatedCSS[Any])(implicit ev: HasProps.NoProps[P]): StyledExternalComponentNoProps[TagType] = {
    new StyledExternalComponentNoProps[TagType](innerObj.asInstanceOf[js.Function].call(
      js.undefined,
      c.parts.toJSArray +: c.interpolations: _*
    ).asInstanceOf[js.Object])
  }
}

final class StyledExternalComponent[P, TagType <: TagElement](comp: js.Object, writer: Writer[P])
  extends ExternalComponentWithAttributes[TagType]()(writer.asInstanceOf[ExternalPropsWriterProvider]) {
  override type Props = P
  override val component: String | js.Object = comp
}

final class StyledExternalComponentNoProps[TagType <: TagElement](comp: js.Object) extends ExternalComponentNoPropsWithAttributes[TagType] {
  override val component: String | js.Object = comp
}
