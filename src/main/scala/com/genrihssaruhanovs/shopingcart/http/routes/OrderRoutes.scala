package com.genrihssaruhanovs.shopingcart.http.routes
import cats.Monad
import com.genrihssaruhanovs.shopingcart.api.Common.{OrderId, User}
import com.genrihssaruhanovs.shopingcart.api.orders.Orders
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.orderCodec
import org.http4s.circe.CirceEntityEncoder._
import cats.syntax.either._
import org.http4s.server.{AuthMiddleware, Router}

import java.util.UUID

final case class OrderRoutes[F[_] : Monad](
  orders: Orders[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/orders"

  private val httpRoutes: AuthedRoutes[User, F] = AuthedRoutes.of {
    case GET -> Root as user =>
      Ok(orders.findBy(user.id))

    case GET -> Root / OrderIdVar(orderId) as user =>
      Ok(orders.get(user.id, orderId))
  }

  object OrderIdVar {
    def unapply(str: String): Option[OrderId] = Either.catchNonFatal(OrderId(UUID.fromString(str))).toOption
  }

  def routes(
    authMiddleware: AuthMiddleware[F, User]
  ): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
