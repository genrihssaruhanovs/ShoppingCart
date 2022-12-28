package com.genrihssaruhanovs.shopingcart.domain
import java.util.UUID

import com.genrihssaruhanovs.shopingcart.domain.Common.Quantity
import io.estatico.newtype.macros.newtype
import squants.market.Money

case class Order(
  id: Order.Id,
  pid: Payment.Id,
  items: Map[Item.Id, Quantity],
  total: Money
)

object Order {
  @newtype case class Id(value: UUID)
}
