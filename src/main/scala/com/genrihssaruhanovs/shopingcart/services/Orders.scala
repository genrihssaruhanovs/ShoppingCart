package com.genrihssaruhanovs.shopingcart.services
import com.genrihssaruhanovs.shopingcart.domain.{Cart, Order, Payment, User}
import squants.market.Money

trait Orders[F[_]] {
  def get(
    userId: User.Id,
    orderId: Order.Id,
  ): F[Option[Order]]

  def findBy(userId: User.Id): F[List[Order]]

  def create(
    userId: User.Id,
    paymentId: Payment.Id,
    items: List[Cart.Item],
    total: Money
  ): F[Order.Id]
}
