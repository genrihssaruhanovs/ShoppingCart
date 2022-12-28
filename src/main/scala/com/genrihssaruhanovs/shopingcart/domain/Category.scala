package com.genrihssaruhanovs.shopingcart.domain
import java.util.UUID

import io.estatico.newtype.macros.newtype

case class Category(uuid: Category.Id, name: Category.Name)

object Category {
  @newtype case class Id(value: UUID)
  @newtype case class Name(value: String)
}
