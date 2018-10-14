package slinky.styledcomponents

import scala.scalajs.js
import js.JSConverters._

import scala.language.implicitConversions

trait InterpolationPart[-P] extends js.Object
trait KeyframesInterpolationPart[-P] extends InterpolationPart[P]

object InterpolationPart {
  implicit def fromPropsFunction[P, O](fn: P => O)(implicit ev: O => InterpolationPart[P]): InterpolationPart[P] = {
    (((o: js.Any) => {
      fn(o.asInstanceOf[js.Dynamic].__.asInstanceOf[P])
    }): js.Function1[js.Any, js.Any]).asInstanceOf[InterpolationPart[P]]
  }

  implicit def fromString(str: String): InterpolationPart[Any] = str.asInstanceOf[InterpolationPart[Any]]

  implicit def fromCSS[P](css: InterpolatedCSS[P]): InterpolationPart[P] = {
    StyledComponentsNamespace.css.call(
      null,
      css.parts.toJSArray +: css.interpolations: _*
    ).asInstanceOf[InterpolationPart[P]]
  }
}
