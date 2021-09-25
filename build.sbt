import Dependencies._

name := "ShoppingCart"

version := "0.1"

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  squants,
  newType,
)

scalacOptions ++= Seq(
  "-Ymacro-annotations"
)
