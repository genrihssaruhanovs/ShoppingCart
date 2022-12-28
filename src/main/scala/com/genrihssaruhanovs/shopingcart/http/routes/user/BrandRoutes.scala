package com.genrihssaruhanovs.shopingcart.http.routes.user

import cats.Monad
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.brandCodec
import com.genrihssaruhanovs.shopingcart.services.Brands
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router

final case class BrandRoutes[F[_] : Monad](
  brands: Brands[F]
) extends Http4sDsl[F] {

  private[user] val prefixPath = "/brands"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root => Ok(brands.findAll)
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
