package com.genrihssaruhanovs.shopingcart.api.category
import com.genrihssaruhanovs.shopingcart.api.Common.{Category, CategoryId, CategoryName}

trait Categories[F[_]] {
  def findAll: F[List[Category]]
  def create(name: CategoryName): F[CategoryId]
}
