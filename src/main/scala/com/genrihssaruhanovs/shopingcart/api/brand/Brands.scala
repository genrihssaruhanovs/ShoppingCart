package com.genrihssaruhanovs.shopingcart.api.brand
import com.genrihssaruhanovs.shopingcart.api.Common.{Brand, BrandId, BrandName}

trait Brands[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: BrandName): F[BrandId]
}
