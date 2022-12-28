package com.genrihssaruhanovs.shopingcart.http.routes.user

import cats.Monad
import com.genrihssaruhanovs.shopingcart.database.HealthCheck
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.appStatusEncoder
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

class HealthRoutes[F[_] : Monad](
  healthCheck: HealthCheck[F]
) extends Http4sDsl[F] {

  private[user] val prefixPath = "/healthCheck"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root => Ok(healthCheck.status)
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
