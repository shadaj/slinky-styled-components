package slinky.styledcomponents

import scala.scalajs.js
import slinky.core.{ExternalComponentNoPropsWithAttributesWithRefType, ExternalComponentWithAttributesWithRefType, TagElement}

trait StyledCore {
  def apply[TagType <: TagElement](component: ExternalComponentNoPropsWithAttributesWithRefType[TagType, _]): StyledBuilder[Any, TagType] = {
    new StyledBuilder[Any, TagType](
      StyledComponents.asInstanceOf[js.Function].call(
        js.undefined,
        component.component.asInstanceOf[js.Any]
      ).asInstanceOf[js.Object]
    )
  }

  def apply[TagType <: TagElement](component: ExternalComponentWithAttributesWithRefType[TagType, _]): StyledBuilder[component.Props, TagType] = {
    new StyledBuilder[component.Props, TagType](
      StyledComponents.asInstanceOf[js.Function].call(
        js.undefined,
        component.component.asInstanceOf[js.Any]
      ).asInstanceOf[js.Object]
    )
  }
}
