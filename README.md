# slinky-styled-components
_use [styled-components](https://www.styled-components.com/) from Slinky apps!_

<p align="center">
  <a href="https://travis-ci.com/shadaj/slinky-styled-components">
    <img src="https://travis-ci.com/shadaj/slinky-styled-components.svg?branch=master"/>
  </a>
  <a href="https://www.scala-js.org">
    <img src="https://www.scala-js.org/assets/badges/scalajs-0.6.17.svg"/>
  </a>
  <img src="https://img.shields.io/maven-central/v/me.shadaj/slinky-styled-components_sjs0.6_2.12.svg"/>
</p>

## Getting Started
Add the library to your build:
```scala
libraryDependencies += "me.shadaj" %%% "slinky-styled-components" % "0.1.0"
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
val styledButtonDynamicData = styled.button[Props](
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
