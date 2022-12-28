package com.genrihssaruhanovs.shopingcart.services
import com.genrihssaruhanovs.shopingcart.domain.{Brand, Category, Item}
import squants.market.Money

trait Items[F[_]] {
  def findAll: F[List[Item]]

  def findBy(brand: Brand.Name): F[List[Item]]

  def findById(id: Item.Id): F[Option[Item]]

  def create(
    name: Item.Name,
    description: Item.Description,
    price: Money,
    brand: Brand,
    category: Category
  ): F[Item.Id]

  def update(id: Item.Id, price: Money): F[Unit]
}
