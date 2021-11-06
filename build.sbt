import Dependencies._

name := "ShoppingCart"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  squants,
  newType,
) ++
  Monocle.libs ++
  Cats.libs ++
  Http4s.libs ++
  Circe.libs

scalacOptions ++= Seq(
  "-Ymacro-annotations",
)
