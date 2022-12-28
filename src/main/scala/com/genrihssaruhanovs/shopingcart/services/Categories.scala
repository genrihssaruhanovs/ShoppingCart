package com.genrihssaruhanovs.shopingcart.services
import com.genrihssaruhanovs.shopingcart.domain.Category

trait Categories[F[_]] {
  def findAll: F[List[Category]]
  def create(name: Category.Name): F[Category.Id]
}
