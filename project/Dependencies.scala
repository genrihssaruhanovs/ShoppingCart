import sbt._

object Dependencies {
  private val squantsVersion = "1.6.0"
  val squants = "org.typelevel" %% "squants" % squantsVersion

  private val newTypeVersion = "0.4.4"
  val newType = "io.estatico" %% "newtype" % newTypeVersion
}
