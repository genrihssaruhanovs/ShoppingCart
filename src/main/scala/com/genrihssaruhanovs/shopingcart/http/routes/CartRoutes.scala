package com.genrihssaruhanovs.shopingcart.http.routes
import cats.Monad
import cats.implicits.{catsSyntaxApply, toFlatMapOps, toTraverseOps}
import com.genrihssaruhanovs.shopingcart.api.Common.{Cart, ItemId, User}
import com.genrihssaruhanovs.shopingcart.api.cart.ShoppingCart
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.dsl.Http4sDsl
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.{cartDecoder, cartEncoder}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.{JsonDecoder, toMessageSyntax}
import org.http4s.server.{AuthMiddleware, Router}
import cats.syntax.either._

import java.util.UUID

final case class CartRoutes[F[_] : Monad : JsonDecoder](
  shoppingCart: ShoppingCart[F]
) extends Http4sDsl[F] {

  private[routes] val prefixPath = "/cart"

  private val httpRoutes: AuthedRoutes[User, F] =
    AuthedRoutes.of {
      // Get shopping cart
      case GET -> Root as user =>
        Ok(shoppingCart.get(user.id))

      // Add items to the cart
      case ar @ POST -> Root as user =>
        ar.req.asJsonDecode[Cart].flatMap {
          _.items.map {
            case (id, quantity) => shoppingCart.add(user.id, id, quantity)
          }
            .toList
            .sequence *> Created()
        }

      // Modify items in the cart
      case ar @ PUT -> Root as user =>
        ar.req.asJsonDecode[Cart].flatMap {
          cart => shoppingCart.update(user.id, cart) *> Ok()
        }

      //Remove item from the cart

      case DELETE -> Root / ItemIdVar(itemId) as user =>
        shoppingCart.removeItem(user.id, itemId) *> NoContent()
    }

  def routes(
    authMiddleware: AuthMiddleware[F, User]
  ): HttpRoutes[F] = Router(
    prefixPath -> authMiddleware(httpRoutes)
  )

  object ItemIdVar {
    def unapply(str: String): Option[ItemId] = Either.catchNonFatal(ItemId(UUID.fromString(str))).toOption
  }
}
