package com.genrihssaruhanovs.shopingcart.http.routes.user

import java.util.UUID

import cats.Monad
import cats.implicits.catsSyntaxEitherObject
import com.genrihssaruhanovs.shopingcart.domain.{Order, User}
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.orderCodec
import com.genrihssaruhanovs.shopingcart.services.Orders
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}
final case class OrderRoutes[F[_] : Monad](
  orders: Orders[F]
) extends Http4sDsl[F] {

  private[user] val prefixPath = "/orders"

  private val httpRoutes: AuthedRoutes[User, F] = AuthedRoutes.of {
    case GET -> Root as user =>
      Ok(orders.findBy(user.id))

    case GET -> Root / OrderIdVar(orderId) as user =>
      Ok(orders.get(user.id, orderId))
  }

  object OrderIdVar {
    def unapply(str: String): Option[Order.Id] = Either.catchNonFatal(Order.Id(UUID.fromString(str))).toOption
  }

  def routes(
    authMiddleware: AuthMiddleware[F, User]
  ): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
