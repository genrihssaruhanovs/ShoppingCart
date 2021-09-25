package com.genrihssaruhanovs.shopingcart.model.endpoints

import com.genrihssaruhanovs.shopingcart.model.Common.{Cart, ItemId, Quantity, UserId}

trait ShoppingCart[F[_]] {
  def add(
    userId: UserId,
    itemId: ItemId,
    quantity: Quantity,
  ): F[Unit]

  def get(
    userId: UserId
  ): F[Cart]

  def delete(userId: UserId): F[Unit]

  def removeItem(userId: UserId, itemId: ItemId): F[Unit]

  def update(userId: UserId, cart: Cart): F[Unit]
}
