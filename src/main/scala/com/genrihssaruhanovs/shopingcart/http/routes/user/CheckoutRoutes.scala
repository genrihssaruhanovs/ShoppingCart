package com.genrihssaruhanovs.shopingcart.http.routes.user

import cats.MonadThrow
import cats.implicits.{catsSyntaxApplicativeError, toFlatMapOps}
import com.genrihssaruhanovs.shopingcart.domain.{Card, User}
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.{cardDecoder, orderIdCodec}
import com.genrihssaruhanovs.shopingcart.json.RefinedDecoder.RefinedRequestDecoder
import com.genrihssaruhanovs.shopingcart.programs.Checkout
import com.genrihssaruhanovs.shopingcart.utility.Error.{CartNotFound, EmptyCartError, OrderOrPaymentError}
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class CheckoutRoutes[F[_] : MonadThrow : JsonDecoder](
  checkout: Checkout[F]
) extends Http4sDsl[F] {

  private[user] val prefixPath = "/checkout"

  private val httpRoutes: AuthedRoutes[User, F] =
    AuthedRoutes.of {
      case ar @ POST -> Root as user =>
        ar.req.decodeR[Card] { card =>
          checkout.process(user.id, card)
            .flatMap(Created(_))
            .recoverWith {
              case CartNotFound(userId) => NotFound(
                  s"Cart not found for user: ${ userId.value }"
                )
              case EmptyCartError         => BadRequest("Shopping cart is empty!")
              case e: OrderOrPaymentError => BadRequest(e.getMessage)
            }
        }
    }

  def routes(
    authMiddleware: AuthMiddleware[F, User]
  ): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )
}
