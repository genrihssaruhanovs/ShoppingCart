package com.genrihssaruhanovs.shopingcart.http.routes

import cats.MonadThrow
import cats.implicits.{catsSyntaxApplicativeError, toFlatMapOps}
import com.genrihssaruhanovs.shopingcart.api.Common.{Card, User}
import com.genrihssaruhanovs.shopingcart.api.cart.Checkout
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.{cardDecoder, orderIdCodec}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.{JsonDecoder, toMessageSyntax}
import org.http4s.server.{AuthMiddleware, Router}
import com.genrihssaruhanovs.shopingcart.utility.Error.{CartNotFound, EmptyCartError}

final case class CheckoutRoutes[F[_] : MonadThrow : JsonDecoder](
  checkout: Checkout[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/checkout"

  private val httpRoutes: AuthedRoutes[User, F] =
    AuthedRoutes.of {
      case ar @ POST -> Root as user =>
        ar.req.asJsonDecode[Card].flatMap { card =>
          checkout.process(user.id, card)
            .flatMap(Created(_))
            .recoverWith {
              case CartNotFound(userId) => NotFound(
                  s"Cart not found for user: ${ userId.value }"
                )
              case EmptyCartError => BadRequest("Shopping cart is empty!")
            }

        }
    }

  def routes(
    authMiddleware: AuthMiddleware[F, User]
  ): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
