package com.genrihssaruhanovs.shopingcart.http.routes.user

import cats.Monad
import com.genrihssaruhanovs.shopingcart.http.routes.query.ParamMatcher
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.itemCodec
import com.genrihssaruhanovs.shopingcart.services.Items
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
final case class ItemRoutes[F[_] : Monad](
  items: Items[F]
) extends Http4sDsl[F] {

  private[user] val prefixPath = "/items"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root :? ParamMatcher.Brand(brand) =>
      Ok(brand.fold(items.findAll)(b => items.findBy(b.toDomain)))
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
