package com.genrihssaruhanovs.shopingcart.model.endpoints

import com.genrihssaruhanovs.shopingcart.model.Common.{Cart, Order, OrderId, PaymentId, UserId}
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
    items: List[Cart],
    total: Money
  ): F[OrderId]
}
