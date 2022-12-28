import Dependencies._

name := "ShoppingCart"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  squants,
  newType,
  refined,
) ++
  Monocle.libs ++
  Cats.libs ++
  Http4s.libs ++
  Circe.libs ++
  Profunktor.libs

scalacOptions ++= Seq(
  "-Ymacro-annotations",
)
