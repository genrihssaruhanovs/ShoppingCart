package com.genrihssaruhanovs.shopingcart.database
import com.genrihssaruhanovs.shopingcart.database.HealthCheck.AppStatus
import io.estatico.newtype.macros.newtype
import monocle.Iso

trait HealthCheck[F[_]] {
  def status: F[AppStatus]
}

object HealthCheck {
  sealed trait Status
  object Status {
    case object Okay extends Status
    case object Unreachable extends Status

    val _Bool: Iso[Status, Boolean] =
      Iso[Status, Boolean] {
        case Okay        => true
        case Unreachable => false
      }(if (_) Okay else Unreachable)

  }
  @newtype case class RedisStatus(value: Status)
  @newtype case class PostgresStatus(value: Status)

  case class AppStatus(
    redis: RedisStatus,
    postgres: PostgresStatus,
  )
}
