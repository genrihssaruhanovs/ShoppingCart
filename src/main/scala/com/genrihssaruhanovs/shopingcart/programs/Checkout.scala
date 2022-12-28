package com.genrihssaruhanovs.shopingcart.programs
import cats.MonadThrow
import cats.implicits.{catsSyntaxApplicativeError, catsSyntaxApply, catsSyntaxMonadError, toFlatMapOps, toFunctorOps}
import com.genrihssaruhanovs.shopingcart.domain._
import com.genrihssaruhanovs.shopingcart.http.clients.PaymentClient
import com.genrihssaruhanovs.shopingcart.services.{Orders, ShoppingCart}
import com.genrihssaruhanovs.shopingcart.utility.retries.{Retriable, Retry}
import com.genrihssaruhanovs.shopingcart.utility.Background
import com.genrihssaruhanovs.shopingcart.utility.Error.{EmptyCartError, OrderOrPaymentError}
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

  def process(userId: User.Id, card: Card): F[Order.Id] =
    shoppingCart.get(userId)
      .ensure(EmptyCartError)(_.items.nonEmpty)
      .flatMap {
        case Cart.Total(items, total) =>
          for {
            paymentId <- processPayment(Payment(userId, total, card))
            orderId <- createOrder(userId, paymentId, items, total)
            _ <- shoppingCart.delete(userId).attempt.void
          } yield orderId
      }

  private def processPayment(in: Payment): F[Payment.Id] =
    Retry[F]
      .retry(policy, Retriable.Payments)(payments.process(in))
      .adaptError {
        case e =>
          OrderOrPaymentError(
            Option(e.getMessage).getOrElse("Unknown")
          )
      }

  private def createOrder(
    userId: User.Id,
    paymentId: Payment.Id,
    items: List[Cart.Item],
    total: Money
  ): F[Order.Id] = {
    val action =
      Retry[F]
        .retry(policy, Retriable.Orders)(
          orders.create(userId, paymentId, items, total)
        )
        .adaptError {
          case e => OrderOrPaymentError(e.getMessage)
        }

    def backgroundAction(fa: F[Order.Id]): F[Order.Id] =
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
