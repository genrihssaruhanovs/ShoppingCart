package com.genrihssaruhanovs.shopingcart.domain
import io.estatico.newtype.macros.newtype

object Common {
  type ErrorMessage = String
  @newtype case class Quantity(value: Int)
}
