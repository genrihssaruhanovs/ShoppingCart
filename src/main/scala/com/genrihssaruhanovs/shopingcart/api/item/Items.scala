package com.genrihssaruhanovs.shopingcart.api.item
import com.genrihssaruhanovs.shopingcart.api.Common.{Brand, BrandName, Category, Item, ItemDescription, ItemId, ItemName}
import squants.market.Money

trait Items[F[_]] {
  def findAll: F[List[Item]]

  def findBy(brand: BrandName): F[Item]

  def findById(id: ItemId): F[Item]

  def create(
    name: ItemName,
    description: ItemDescription,
    price: Money,
    brand: Brand,
    category: Category
  ): F[ItemId]

  def update(id: ItemId, price: Money): F[Unit]
}
