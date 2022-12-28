package com.genrihssaruhanovs.shopingcart.services
import com.genrihssaruhanovs.shopingcart.domain.{Cart, Item, User}
import com.genrihssaruhanovs.shopingcart.domain.Common.Quantity

trait ShoppingCart[F[_]] {
  def add(
    userId: User.Id,
    itemId: Item.Id,
    quantity: Quantity,
  ): F[Unit]

  def get(
    userId: User.Id
  ): F[Cart.Total]

  def delete(userId: User.Id): F[Unit]

  def removeItem(userId: User.Id, itemId: Item.Id): F[Unit]

  def update(userId: User.Id, cart: Cart): F[Unit]
}
