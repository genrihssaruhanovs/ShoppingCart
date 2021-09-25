package com.genrihssaruhanovs.shopingcart.model.endpoints

import com.genrihssaruhanovs.shopingcart.model.Common.{Category, CategoryId, CategoryName}

trait Categories[F[_]] {
  def findAll: F[List[Category]]
  def create(name: CategoryName): F[CategoryId]
}
