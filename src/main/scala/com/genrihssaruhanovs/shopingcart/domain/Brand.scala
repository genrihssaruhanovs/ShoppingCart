package com.genrihssaruhanovs.shopingcart.domain
import java.util.UUID

import io.estatico.newtype.macros.newtype

case class Brand(id: Brand.Id, name: Brand.Name)
object Brand {
  @newtype case class Id(value: UUID)
  @newtype case class Name(value: String)
}
