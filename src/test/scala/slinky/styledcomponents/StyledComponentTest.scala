package slinky.styledcomponents

import org.scalajs.dom
import org.scalajs.dom.Element
import org.scalajs.dom.raw.{HTMLButtonElement, HTMLElement}
import org.scalatest.FunSuite
import slinky.web.ReactDOM
import slinky.web.html.id
import slinky.web.html.`type`

class StyledComponentTest extends FunSuite {
  test("Can construct a styled button") {
    case class Props(color: String)
    val targetElem = dom.document.createElement("div")
    val comp = styled.button[Props](
      css"""
        border-radius: 3px;
        color: ${p: Props => p.color}
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
    assert(buttonElement.id == "testComponent")
    assert(buttonElement.className.nonEmpty)
  }

  test("Can construct a styled button with attrs") {
    case class Props(color: String)
    val targetElem = dom.document.createElement("div")
    val comp = styled.button[Props].attrs(
      `type` := "reset"
    )(
      css"""
        border-radius: 3px;
        color: ${p: Props => p.color}
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
    val comp = styled.button[Props](
      css"""
        border-radius: 3px;
        ${
          css"color: ${p: Props => p.color}"
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

    val comp = styled.button[Unit](
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
    val comp = styled.button[Unit](
      css""
    )

    var buttonElement: Element = null

    ReactDOM.render(
      comp()(
        innerRef := (e => {
          buttonElement = e
        })
      ),
      targetElem
    )

    assert(buttonElement.tagName.toLowerCase == "button")
  }
}
