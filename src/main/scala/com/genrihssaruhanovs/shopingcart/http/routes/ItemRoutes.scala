package com.genrihssaruhanovs.shopingcart.http.routes

import cats.Monad
import com.genrihssaruhanovs.shopingcart.api.Common.BrandName
import com.genrihssaruhanovs.shopingcart.api.item.Items
import org.http4s.{HttpRoutes, ParseFailure, QueryParamDecoder}
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.itemCodec
import org.http4s.circe.CirceEntityEncoder._

final case class ItemRoutes[F[_] : Monad](
  items: Items[F]
) extends Http4sDsl[F] {

  object BrandQueryParam extends OptionalQueryParamDecoderMatcher[BrandName]("brand")
  implicit val brandQueryParamDecoder: QueryParamDecoder[BrandName] = QueryParamDecoder[String].emap(x =>
    Either.cond(
      x.isEmpty,
      BrandName(x),
      ParseFailure("String provided is empty", "String provided is empty")
    )
  )

  private[routes] val prefixPath = "/items"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root :? BrandQueryParam(brand) => Ok(brand.fold(items.findAll)(b => items.findBy(b)))
  }

  val routes: HttpRoutes[F] = Router(
    prefixPath -> httpRoutes
  )
}
