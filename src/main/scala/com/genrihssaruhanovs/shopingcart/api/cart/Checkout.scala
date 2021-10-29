package com.genrihssaruhanovs.shopingcart.api.cart
import cats.Monad
import cats.implicits._
import com.genrihssaruhanovs.shopingcart.api.Common.{Card, OrderId, Payment, UserId}
import com.genrihssaruhanovs.shopingcart.api.orders.Orders
import com.genrihssaruhanovs.shopingcart.api.payment.PaymentClient

final case class Checkout[F[_] : Monad](
  payments: PaymentClient[F],
  shoppingCart: ShoppingCart[F],
  orders: Orders[F]
) {
  def process(userId: UserId, card: Card): F[OrderId] = for {
    cart <- shoppingCart.get(userId)
    paymentId <- payments.process(Payment(userId, cart.total, card))
    orderId <- orders.create(userId, paymentId, cart.items, cart.total)
    _ <- shoppingCart.delete(userId)
  } yield orderId
}
