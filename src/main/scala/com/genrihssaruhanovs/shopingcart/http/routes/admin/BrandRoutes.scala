package com.genrihssaruhanovs.shopingcart.http.routes.admin

import cats.MonadThrow
import cats.implicits.toFlatMapOps
import com.genrihssaruhanovs.shopingcart.domain.Brand
import com.genrihssaruhanovs.shopingcart.domain.User.Admin
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.{brandIdCodec, brandNameCodec}
import com.genrihssaruhanovs.shopingcart.json.RefinedDecoder.RefinedRequestDecoder
import com.genrihssaruhanovs.shopingcart.services.Brands
import io.circe.JsonObject
import io.circe.syntax.EncoderOps
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class BrandRoutes[F[_] : JsonDecoder : MonadThrow](
  brands: Brands[F]
) extends Http4sDsl[F] {

  private[admin] val prefixPath = "/brands"

  private val httpRoutes: AuthedRoutes[Admin, F] = AuthedRoutes.of {
    case ar @ POST -> Root as _ =>
      ar.req.decodeR[Brand.Name] { brandName =>
        brands.create(brandName)
          .flatMap { id =>
            Created(JsonObject.singleton("brand_id", id.asJson))
          }
      }
  }

  def routes(
    authMiddleware: AuthMiddleware[F, Admin]
  ): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
