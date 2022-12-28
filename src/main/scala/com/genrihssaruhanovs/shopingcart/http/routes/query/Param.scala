package com.genrihssaruhanovs.shopingcart.http.routes.query
import com.genrihssaruhanovs.shopingcart.domain.Brand.{Name => BrandName}
import eu.timepit.refined.auto.autoUnwrap
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype

object Param {
  @newtype case class Brand(value: NonEmptyString) {
    def toDomain: BrandName =
      BrandName(value.toLowerCase.capitalize)
  }
}
