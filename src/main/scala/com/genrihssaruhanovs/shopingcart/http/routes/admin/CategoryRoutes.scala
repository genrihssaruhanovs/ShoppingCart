package com.genrihssaruhanovs.shopingcart.http.routes.admin

import cats.MonadThrow
import cats.implicits.toFlatMapOps
import com.genrihssaruhanovs.shopingcart.domain.Category
import com.genrihssaruhanovs.shopingcart.domain.User.Admin
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.{categoryIdCodec, categoryNameCodec}
import com.genrihssaruhanovs.shopingcart.json.RefinedDecoder.RefinedRequestDecoder
import com.genrihssaruhanovs.shopingcart.services.Categories
import io.circe.JsonObject
import io.circe.syntax.EncoderOps
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class CategoryRoutes[F[_] : JsonDecoder : MonadThrow](
  categories: Categories[F]
) extends Http4sDsl[F] {

  private[admin] val prefixPath = "/categories"

  private val httpRoutes: AuthedRoutes[Admin, F] = AuthedRoutes.of {
    case ar @ POST -> Root as _ =>
      ar.req.decodeR[Category.Name] { categoryName =>
        categories.create(categoryName)
          .flatMap { id =>
            Created(JsonObject.singleton("category_id", id.asJson))
          }
      }
  }

  def routes(
    authMiddleware: AuthMiddleware[F, Admin]
  ): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
