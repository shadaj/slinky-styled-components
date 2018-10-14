package slinky.styledcomponents

import slinky.core.{AttrPair, ExternalComponentWithAttributes, BuildingComponent, ExternalPropsWriterProvider, TagElement}
import slinky.readwrite.Writer
import slinky.core.facade.{React, ReactElement}

import scala.scalajs.js
import scala.scalajs.js.|
import js.JSConverters._

final class StyledBuilder[P, TagType <: TagElement](private val innerObj: js.Object) extends AnyVal {
  def attrs(pairs: AttrPair[TagType]*): StyledBuilder[P, TagType] = {
    val dictionary = js.Dictionary[js.Any]()
    pairs.foreach(p => dictionary(p.name) = p.value)
    new StyledBuilder(innerObj.asInstanceOf[js.Dynamic].attrs(dictionary).asInstanceOf[js.Object])
  }

  def apply(c: InterpolatedCSS[P]): StyledExternalComponent[P, TagType] = {
    new StyledExternalComponent[P, TagType](innerObj.asInstanceOf[js.Function].call(
      js.undefined,
      c.parts.toJSArray +: c.interpolations: _*
    ).asInstanceOf[js.Object])
  }
}

class StyledExternalComponent[P, TagType <: TagElement](comp: js.Object) extends ExternalComponentWithAttributes[TagType]()(new Writer[P] {
  override def write(p: P): js.Object = js.Dynamic.literal(__ = p.asInstanceOf[js.Object])
}.asInstanceOf[ExternalPropsWriterProvider]) {
  override type Props = P
  override val component: String | js.Object = comp

  def apply(mod: AttrPair[TagType], tagMods: AttrPair[TagType]*): BuildingComponent[TagType, js.Object] = {
    new BuildingComponent(component, js.Dynamic.literal(), mods = mod +: tagMods)
  }

  def withKey(key: String): BuildingComponent[TagType, js.Object] = new BuildingComponent(component, js.Dynamic.literal(), key = key)
  def withRef(ref: js.Object => Unit): BuildingComponent[TagType, js.Object] = new BuildingComponent(component, js.Dynamic.literal(), ref = ref)

  def apply(children: ReactElement*): ReactElement = {
    React.createElement(component, js.Dynamic.literal().asInstanceOf[js.Dictionary[js.Any]], children: _*)
  }
}
