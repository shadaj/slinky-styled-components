package slinky.styledcomponents

import scala.scalajs.js
import slinky.core.{ExternalComponentNoPropsWithAttributesWithRefType, ExternalComponentWithAttributesWithRefType, ReactComponentClass, TagElement}
import slinky.readwrite.Writer

trait StyledCore {
  def apply[TagType <: TagElement](component: ExternalComponentNoPropsWithAttributesWithRefType[TagType, _]): StyledBuilder[Any, TagType] = {
    new StyledBuilder[Any, TagType](
      StyledComponents(
        component.component.asInstanceOf[js.Any]
      )
    )
  }

  def apply[TagType <: TagElement](component: ExternalComponentWithAttributesWithRefType[TagType, _]): StyledBuilder[component.Props, TagType] = {
    new StyledBuilder[component.Props, TagType](
      StyledComponents(
        component.component.asInstanceOf[js.Any]
      )
    )
  }

  def apply[P](clazz: ReactComponentClass[P])(implicit propsWriter: Writer[P]): StyledBuilder[P, TagElement] = {
    new StyledBuilder[P, TagElement](
      StyledComponents(
        clazz.asInstanceOf[js.Any]
      ),
      propsWriter
    )
  }
}
