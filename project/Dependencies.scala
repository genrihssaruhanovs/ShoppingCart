import sbt._

object Dependencies {
  private val squantsVersion = "1.8.3"
  val squants = "org.typelevel" %% "squants" % squantsVersion

  private val newTypeVersion = "0.4.4"
  val newType = "io.estatico" %% "newtype" % newTypeVersion

  object Cats {
    private val retryVersion = "3.1.0"
    private val coreVersion = "2.6.1"
    private val loggerVersion = "2.1.1"

    private val core = "org.typelevel" %% "cats-core" % coreVersion
    private val retry = "com.github.cb372" %% "cats-retry" % retryVersion
    private val loggerCore = "org.typelevel" %% "log4cats-core" % loggerVersion
    private val loggerslf4j = "org.typelevel" %% "log4cats-slf4j" % loggerVersion

    val libs = Seq(
      core,
      retry,
      loggerCore,
      loggerslf4j
    )
  }

  object Monocle {
    private val version = "3.1.0"
    private val core = "dev.optics" %% "monocle-core"
    private val monocleMacro = "dev.optics" %% "monocle-macro"

    val libs = Seq(
      core,
      monocleMacro,
    ).map(_ % version)
  }

  object Http4s {
    private val version = "0.23.6"
    private val dsl = "org.http4s" %% "http4s-dsl"
    private val blazeServer = "org.http4s" %% "http4s-blaze-server"
    private val circe = "org.http4s" %% "http4s-circe"
    val libs = Seq(
      dsl,
      blazeServer,
      circe,
    ).map(_ % version)
  }

  object Circe {
    private val version = "0.14.1"
    private val core = "io.circe" %% "circe-core"
    private val generic = "io.circe" %% "circe-generic"
    private val parser = "io.circe" %% "circe-parser"
    private val literal = "io.circe" %% "circe-literal"

    val libs = Seq(
      core,
      generic,
      parser,
      literal,
    ).map(_ % version)
  }

}
