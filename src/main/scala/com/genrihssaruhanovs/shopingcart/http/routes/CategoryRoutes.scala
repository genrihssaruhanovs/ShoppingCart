package com.genrihssaruhanovs.shopingcart.http.routes

import cats.Monad
import com.genrihssaruhanovs.shopingcart.api.category.Categories
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.categoryCodec
import org.http4s.circe.CirceEntityEncoder._

final case class CategoryRoutes[F[_] : Monad](
  categories: Categories[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/categories"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root => Ok(categories.findAll)
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
