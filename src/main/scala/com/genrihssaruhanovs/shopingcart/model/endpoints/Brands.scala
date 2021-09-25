package com.genrihssaruhanovs.shopingcart.model.endpoints

import com.genrihssaruhanovs.shopingcart.model.Common.{Brand, BrandId, BrandName}

trait Brands[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: BrandName): F[BrandId]
}
