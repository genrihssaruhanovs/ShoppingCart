package com.genrihssaruhanovs.shopingcart.domain
import java.util.UUID

import io.estatico.newtype.macros.newtype
import squants.market.Money

case class Item(
  uuid: Item.Id,
  name: Item.Name,
  description: Item.Description,
  price: Money,
  brand: Brand,
  category: Category
)

object Item {
  @newtype case class Id(value: UUID)
  @newtype case class Name(value: String)
  @newtype case class Description(value: String)

  case class Create(
    name: Item.Name,
    description: Item.Description,
    price: Money,
    brandId: Brand.Id,
    categoryId: Category.Id
  )

  case class Update(
    id: Item.Id,
    price: Money
  )
}
