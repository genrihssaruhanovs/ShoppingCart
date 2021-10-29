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
    private val monocleCore = "dev.optics" %% "monocle-core" % version
    private val monocleMacro = "dev.optics" %% "monocle-macro" % version
    val libs = Seq(
      monocleCore,
      monocleMacro
    )
  }

}
