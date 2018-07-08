package slinky.styledcomponents

import org.scalajs.dom
import org.scalajs.dom.raw.HTMLButtonElement
import org.scalatest.FunSuite
import slinky.web.ReactDOM

import slinky.web.html.id

class StyledComponentTest extends FunSuite {
  test("Can construct a styled button") {
    case class Props(color: String)
    val targetElem = dom.document.createElement("div")
    val comp = styled[Props](
      button"""
        border-radius: 3px;
        color: ${_.color}
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
}
