package slinky

import slinky.core.{ExternalComponentWithAttributes, ExternalPropsWriterProvider, Tag, TagElement}
import slinky.readwrite.Writer
import slinky.web.html

import scala.scalajs.js
import js.JSConverters._
import scala.scalajs.js.|

package object styledcomponents {
  private def elemHandler[P, T <: TagElement](sc: StringContext, comp: js.Function, fns: Seq[P => js.Any]): StyledComponentConstructor[P] { type TagType = T } = {
    val jsFns = fns.map(v => ((o: js.Any) => { v(o.asInstanceOf[js.Dynamic].__.asInstanceOf[P]) }): js.Function1[js.Any, js.Any])
    StyledComponents.button.call(
      js.undefined,
      sc.parts.toJSArray +: jsFns: _*
    ).asInstanceOf[StyledComponentConstructor[P] { type TagType = T }]
  }

  implicit class StyledInterpolator(val sc: StringContext) extends AnyVal {
    def button[P](fns: (P => js.Any)*) =
      elemHandler[P, html.button.tagType](sc, StyledComponents.button, fns)
  }

  def styled[P](c: StyledComponentConstructor[P]): ExternalComponentWithAttributes[c.TagType] { type Props = P } = {
    new ExternalComponentWithAttributes[c.TagType]()(new Writer[P] {
      override def write(p: P): js.Object = js.Dynamic.literal(__ = p.asInstanceOf[js.Object])
    }.asInstanceOf[ExternalPropsWriterProvider]) {
      override type Props = P
      override val component: String | js.Object = c
    }
  }
}
