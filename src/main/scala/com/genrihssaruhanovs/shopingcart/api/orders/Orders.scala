package com.genrihssaruhanovs.shopingcart.api.orders
import com.genrihssaruhanovs.shopingcart.api.Common.{Cart, ItemId, Order, OrderId, PaymentId, Quantity, UserId}
import squants.market.Money

trait Orders[F[_]] {
  def get(
    userId: UserId,
    orderId: OrderId,
  ): F[Option[Order]]

  def findBy(userId: UserId): F[List[Order]]

  def create(
    userId: UserId,
    paymentId: PaymentId,
    items: Map[ItemId, Quantity],
    total: Money
  ): F[OrderId]
}
