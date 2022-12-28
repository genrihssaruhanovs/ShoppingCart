package com.genrihssaruhanovs.shopingcart.domain
import java.util.UUID

import io.estatico.newtype.macros.newtype
import squants.market.Money

case class Payment(
  id: User.Id,
  total: Money,
  card: Card
)

object Payment {
  @newtype case class Id(value: UUID)
}
