package com.genrihssaruhanovs.shopingcart.domain
import com.genrihssaruhanovs.shopingcart.domain.Common.Quantity
import io.estatico.newtype.macros.newtype
import squants.market.Money

@newtype case class Cart(items: Map[Item.Id, Quantity])

object Cart {
  case class Item(item: Item, quantity: Quantity)
  case class Total(items: List[Cart.Item], total: Money)
}
