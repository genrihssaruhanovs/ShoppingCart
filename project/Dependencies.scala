import sbt._

object Dependencies {
  private val squantsVersion = "1.6.0"
  val squants = "org.typelevel" %% "squants" % squantsVersion

  private val newTypeVersion = "0.4.4"
  val newType = "io.estatico" %% "newtype" % newTypeVersion

  private val catsVersion = "2.3.0"
  val catsCore = "org.typelevel" %% "cats-core" % catsVersion

  object Monocle {
    private val version = "3.0.0"
    private val monocleCore = "dev.optics" %% "monocle-core" % version
    private val monocleMacro = "dev.optics" %% "monocle-macro" % version
    val libs = Seq(
      monocleCore,
      monocleMacro
    )
  }

}
