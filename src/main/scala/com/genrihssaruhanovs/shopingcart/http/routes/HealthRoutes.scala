package com.genrihssaruhanovs.shopingcart.http.routes
import cats.Monad

import com.genrihssaruhanovs.shopingcart.db.HealthCheck
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.appStatusEncoder
import org.http4s.circe.CirceEntityEncoder._

class HealthRoutes[F[_] : Monad](
  healthCheck: HealthCheck[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/healthCheck"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root => Ok(healthCheck.status)
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
