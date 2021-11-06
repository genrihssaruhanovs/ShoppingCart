package com.genrihssaruhanovs.shopingcart.api.cart
import cats.MonadThrow
import cats.implicits._
import com.genrihssaruhanovs.shopingcart.api.Common.{Card, ItemId, OrderId, Payment, PaymentId, Quantity, UserId}
import com.genrihssaruhanovs.shopingcart.api.orders.Orders
import com.genrihssaruhanovs.shopingcart.api.payment.PaymentClient
import com.genrihssaruhanovs.shopingcart.utility.Background
import com.genrihssaruhanovs.shopingcart.utility.Error.{EmptyCartError, OrderError, PaymentError}
import com.genrihssaruhanovs.shopingcart.utility.retries.{Retriable, Retry}
import org.typelevel.log4cats.Logger
import retry.RetryPolicy
import squants.market.Money

import scala.concurrent.duration.DurationInt

final case class Checkout[F[_] : MonadThrow : Retry : Logger : Background](
  payments: PaymentClient[F],
  shoppingCart: ShoppingCart[F],
  orders: Orders[F],
  policy: RetryPolicy[F]
) {

  def process(userId: UserId, card: Card): F[OrderId] =
    shoppingCart.get(userId)
      .ensure(EmptyCartError)(_.items.nonEmpty)
      .flatMap(cart =>
        for {
          paymentId <- processPayment(Payment(userId, cart.total, card))
          orderId <- createOrder(userId, paymentId, cart.items, cart.total)
          _ <- shoppingCart.delete(userId)

        } yield orderId
      )

  private def processPayment(in: Payment): F[PaymentId] =
    Retry[F]
      .retry(policy, Retriable.Payments)(payments.process(in))
      .adaptError {
        case e =>
          PaymentError(
            Option(e.getMessage).getOrElse("Unknown")
          )
      }

  private def createOrder(
    userId: UserId,
    paymentId: PaymentId,
    items: Map[ItemId, Quantity],
    total: Money
  ): F[OrderId] = {
    val action =
      Retry[F]
        .retry(policy, Retriable.Orders)(
          orders.create(userId, paymentId, items, total)
        )
        .adaptError {
          case e => OrderError(e.getMessage)
        }

    def backgroundAction(fa: F[OrderId]): F[OrderId] =
      fa.onError {
        case _ =>
          Logger[F].error(
            s"Failed to create order for: $paymentId" //TODO paymentId.show
          ) *>
            Background[F].schedule(backgroundAction(fa), 1.hour)
      }

    backgroundAction(action)
  }
}
