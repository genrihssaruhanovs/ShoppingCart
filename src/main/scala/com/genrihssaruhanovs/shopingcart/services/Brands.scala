package com.genrihssaruhanovs.shopingcart.services
import com.genrihssaruhanovs.shopingcart.domain.Brand

trait Brands[F[_]] {
  def findAll: F[List[Brand]]
  def create(name: Brand.Name): F[Brand.Id]
}
