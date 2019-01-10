<h1 align="center">slinky-styled-components</h1>
<p align="center"><i>use <a href="https://www.styled-components.com">styled-components</a> from Slinky apps!</i></p>

<p align="center">
  <a href="https://travis-ci.com/shadaj/slinky-styled-components">
    <img src="https://travis-ci.com/shadaj/slinky-styled-components.svg?branch=master"/>
  </a>
  <a href="https://www.scala-js.org">
    <img src="https://www.scala-js.org/assets/badges/scalajs-0.6.17.svg"/>
  </a>
  <a href="https://maven-central-latest.netlify.com/.netlify/functions/latest-link?org=me.shadaj&name=slinky-styled-components_sjs0.6_2.12&format=libraryDependencies%20%2B%3D%20%22me.shadaj%22%20%25%25%25%20%22slinky-styled-components%22%20%25%20%22VERSION%22">
  <img src="https://maven-central-latest.netlify.com/.netlify/functions/latest-link?org=me.shadaj&name=slinky-styled-components_sjs0.6_2.12&formatRedirect=https%3A%2F%2Fimg.shields.io%2Fbadge%2Fmaven--central-VERSIONDOUBLEDASHED-orange.svg"/>
  </a>
</p>

## Getting Started
Add the library to your build and `styled-components` for Webpack bundling:
```scala
libraryDependencies += "me.shadaj" %%% "slinky-styled-components" % "0.1.0"

npmDependencies += "styled-components" -> "3.4.10"
```

## Creating Components
Let's see how to create styled components with a simple example of a colored button:

```scala
import slinky.styledcomponents._

val styledButton = styled.button(
  css"""
    color: green;
  """
)
```

We can then use this in our app just like a regular component:
```scala
div(
  styledButton(id := "my-button")(
    "Hello World!"
  )
)
```

If we want to calculate styles based on some dynamic data, we can use props:
```scala
case class StyledButtonProps(color: String)
val styledButtonDynamicData = styled.button(
  css"""
    color: ${p: Props => p.color}
  """
)

div(
  styledButtonDynamicData(StyledButtonProps("pink"))(
    "Hello, this button is pink!"
  )
)
```

## Extending Components
You can extend existing components with additional styles. For example, you could extend a styled button with more CSS:

```scala
case class Props(color: String)

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
```

You can also extend Slinky components to assign them more styles, with the requirement that the component must take a `className` prop.

```scala
@react class IntHeaderComponent extends StatelessComponent {
  case class Props(number: Int, className: String = "")

  override def render(): ReactElement = {
    h1(className := props.className)(props.number.toString)
  }
}

// ...

val styledIntHeader = styled(IntHeaderComponent).apply[IntHeaderComponent.Props](
  css"""
    color: green;
  """
)

// ...

styledIntHeader(IntHeaderComponent.Props(123))
```

## CSS Animations
The key feature of `styled-components` is the ability to use all CSS features, even ones like CSS animations. To build an animation that uses key frames, you can use the `keyframes` string interpolator.

```scala
val fadeIn = keyframes"""
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
"""

val fadedButton = styled.button(
  css"""
    animation: 1s $fadeIn ease-out;
  """
)
```

## CSS Fragments
You can store `css` interpolations as variables and reuse them across multiple components. For example, you can use a shared color styling fragment.

```scala
val colorCSS = css"color: pink"
val styledButton = styled.button(
  css"""
    border-radius: 3px;
    $colorCSS
  """
)

val styledDiv = styled.div(
  css"""
    border-radius: 10px;
    $colorCSS
  """
)
```
