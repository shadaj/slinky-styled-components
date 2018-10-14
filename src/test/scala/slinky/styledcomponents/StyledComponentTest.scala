package slinky.styledcomponents

import org.scalajs.dom
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLButtonElement
import org.scalatest.FunSuite

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.ReactDOM
import slinky.web.html.{`type`, className, h1, id}

@react class TestComponentToExtend extends StatelessComponent {
  case class Props(a: Int, className: String = "")

  override def render(): ReactElement = {
    h1(className := props.className)(props.a.toString)
  }
}

class StyledComponentTest extends FunSuite {
  test("Can construct a styled button with no props") {
    val targetElem = dom.document.createElement("div")
    val comp = styled.button(
      css"""
        border-radius: 3px;
        color: green;
      """
    )

    ReactDOM.render(
      comp(
        id := "testComponent"
      ),
      targetElem
    )

    val buttonElement = targetElem.firstElementChild.asInstanceOf[HTMLButtonElement]

    assert(buttonElement.tagName.toLowerCase == "button")
    assert(buttonElement.id == "testComponent")
    assert(buttonElement.className.nonEmpty)
  }

  test("Can construct a styled button with some props") {
    case class Props(color: String)
    val targetElem = dom.document.createElement("div")
    val comp = styled.button(
      css"""
        border-radius: 3px;
        color: ${p: Props => p.color};
      """
    )

    ReactDOM.render(
      comp.apply(Props(color = "pink"))(
        id := "testComponent"
      ),
      targetElem
    )

    val buttonElement = targetElem.firstElementChild.asInstanceOf[HTMLButtonElement]

    assert(buttonElement.tagName.toLowerCase == "button")
    assert(buttonElement.id == "testComponent")
    assert(buttonElement.className.nonEmpty)
  }

  test("Can construct a styled button with attrs") {
    case class Props(color: String)
    val targetElem = dom.document.createElement("div")
    val comp = styled.button.attrs(
      `type` := "reset"
    )(
      css"""
        border-radius: 3px;
        color: ${p: Props => p.color};
      """
    )

    ReactDOM.render(
      comp(Props(color = "pink"))(
        id := "testComponent"
      ),
      targetElem
    )

    val buttonElement = targetElem.firstElementChild.asInstanceOf[HTMLButtonElement]

    assert(buttonElement.tagName.toLowerCase == "button")
    assert(buttonElement.`type` == "reset")
    assert(buttonElement.id == "testComponent")
    assert(buttonElement.className.nonEmpty)
  }

  test("Can construct a styled button with interpolated CSS") {
    case class Props(color: String)
    val targetElem = dom.document.createElement("div")
    val comp = styled.button(
      css"""
        border-radius: 3px;
        ${
          css"color: ${p: Props => p.color};"
        }
      """
    )

    ReactDOM.render(
      comp(Props(color = "pink")),
      targetElem
    )

    val buttonElement = targetElem.firstElementChild.asInstanceOf[HTMLButtonElement]

    assert(buttonElement.tagName.toLowerCase == "button")
    assert(buttonElement.className.nonEmpty)
  }

  test("Can construct a styled button with keyframes") {
    val targetElem = dom.document.createElement("div")
    val fadeIn = keyframes"""
      0% {
        opacity: 0;
      }
      
      100% {
        opacity: 1;
      }
    """

    val comp = styled.button(
      css"""
        animation: 1s $fadeIn ease-out;
      """
    )

    ReactDOM.render(
      comp(),
      targetElem
    )

    val buttonElement = targetElem.firstElementChild.asInstanceOf[HTMLButtonElement]

    assert(buttonElement.tagName.toLowerCase == "button")
    assert(buttonElement.className.nonEmpty)
  }

  test("Can pass in inner ref to get DOM element") {
    val targetElem = dom.document.createElement("div")
    val comp = styled.button(
      css""
    )

    var buttonElement: Element = null

    ReactDOM.render(
      comp(
        innerRef := (e => {
          buttonElement = e
        })
      ),
      targetElem
    )

    assert(buttonElement.tagName.toLowerCase == "button")
  }

  test("Can extend a styled button with additional styles") {
    val targetElem = dom.document.createElement("div")
    val baseStyled = styled.button(
      css"""
        border-radius: 3px;
        color: green;
      """
    )

    val extendedStyled = styled(baseStyled)(
      css"""
        font-size: 10px;
      """
    )

    ReactDOM.render(
      extendedStyled(
        id := "testComponent"
      ),
      targetElem
    )

    val buttonElement = targetElem.firstElementChild.asInstanceOf[HTMLButtonElement]

    assert(buttonElement.tagName.toLowerCase == "button")
    assert(buttonElement.id == "testComponent")
    assert(buttonElement.className.nonEmpty)
  }

  test("Can extend a styled button taking props with additional styles") {
    case class Props(color: String)

    val targetElem = dom.document.createElement("div")
    val baseStyled = styled.button(
      css"""
        border-radius: 3px;
        color: ${p: Props => p.color};
      """
    )

    val extendedStyled = styled(baseStyled)(
      css"""
        font-size: 10px;
        background-color: ${p: Props => p.color}
      """
    )

    ReactDOM.render(
      extendedStyled(Props("green"))(
        id := "testComponent"
      ),
      targetElem
    )

    val buttonElement = targetElem.firstElementChild.asInstanceOf[HTMLButtonElement]

    assert(buttonElement.tagName.toLowerCase == "button")
    assert(buttonElement.id == "testComponent")
    assert(buttonElement.className.nonEmpty)
  }

  test("Can extend a Slinky component with additional styles") {
    val targetElem = dom.document.createElement("div")

    val extendedStyled = styled(TestComponentToExtend).apply[TestComponentToExtend.Props](
      css"""
        color: green;
      """
    )

    ReactDOM.render(
      extendedStyled(TestComponentToExtend.Props(123)),
      targetElem
    )

    val buttonElement = targetElem.firstElementChild.asInstanceOf[HTMLButtonElement]

    assert(buttonElement.tagName.toLowerCase == "h1")
    assert(buttonElement.className.nonEmpty)
  }
}
