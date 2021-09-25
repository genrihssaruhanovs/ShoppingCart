package com.genrihssaruhanovs.shopingcart.model

import io.estatico.newtype.macros.newtype
import squants.market.Money

import java.util.UUID

object Common {

  /** User related types
    */
  @newtype case class UserId(value: UUID)
  @newtype case class UserName(value: String)
  @newtype case class Password(value: String)
  @newtype case class EncryptedPassword(value: String)
  @newtype case class JwtToken(value: String)

  /** Brand related types
    */
  @newtype case class BrandId(value: UUID)
  @newtype case class BrandName(value: String)

  /** Category related types
    */
  @newtype case class CategoryId(value: UUID)
  @newtype case class CategoryName(value: String)

  /** Item related types
    */
  @newtype case class ItemId(value: UUID)
  @newtype case class ItemName(value: String)
  @newtype case class ItemDescription(value: String)

  /** Shopping card related types
    */
  @newtype case class Quantity(value: Int)

  /** Order related types
    */
  @newtype case class OrderId(value: UUID)
  @newtype case class PaymentId(value: UUID)

  /** Payment related types
    */
  @newtype case class CardHoldersName(value: String)
  @newtype case class CardNumber(value: String)
  @newtype case class CardExpiration(value: String)
  @newtype case class CardCvv(value: String)

  /** Model Classes begin here
    */
  case class User(id: UserId, name: UserName)

  case class UserWithPassword(
    id: UserId,
    name: UserName,
    password: EncryptedPassword
  )

  case class Brand(uuid: BrandId, name: BrandName)

  case class Category(uuid: CategoryId, name: CategoryName)

  case class Item(
    uuid: ItemId,
    name: ItemName,
    description: ItemDescription,
    price: Money,
    brand: Brand,
    category: Category
  )

  case class Cart(items: Map[ItemId, Quantity]) {
    def total: Money = ??? // TODO - implement once I understand how Money works
  }

  case class Order(
    id: OrderId,
    pid: PaymentId,
    items: Map[ItemId, Quantity],
  ) {
    def total: Money = ??? // TODO - implement once I understand how Money works
  }

  case class Card(
    name: CardHoldersName,
    number: CardNumber,
    expiration: CardExpiration,
    cvv: CardCvv
  )

  case class Payment(
    id: UserId,
    total: Money,
    card: Card
  )
}
