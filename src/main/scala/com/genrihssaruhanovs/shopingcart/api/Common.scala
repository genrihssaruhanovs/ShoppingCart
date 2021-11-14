package com.genrihssaruhanovs.shopingcart.api
import squants.market.Money

import java.util.UUID

object Common {

  /** User related types
    */
  case class UserId(value: UUID) extends AnyVal
  case class UserName(value: String) extends AnyVal
  case class Password(value: String) extends AnyVal
  case class EncryptedPassword(value: String) extends AnyVal
  case class JwtToken(value: String) extends AnyVal

  /** Brand related types
    */
  case class BrandId(value: UUID) extends AnyVal
  case class BrandName(value: String) extends AnyVal

  /** Category related types
    */
  case class CategoryId(value: UUID) extends AnyVal
  case class CategoryName(value: String) extends AnyVal

  /** Item related types
    */
  case class ItemId(value: UUID) extends AnyVal
  case class ItemName(value: String) extends AnyVal
  case class ItemDescription(value: String) extends AnyVal

  /** Shopping card related types
    */
  case class Quantity(value: Int) extends AnyVal

  /** Order related types
    */
  case class OrderId(value: UUID) extends AnyVal
  case class PaymentId(value: UUID) extends AnyVal

  /** Payment related types
    */
  case class CardHoldersName(value: String) extends AnyVal
  case class CardNumber(value: String) extends AnyVal
  case class CardExpiration(value: String) extends AnyVal
  case class CardCvv(value: String) extends AnyVal

  /** Model Classes begin here
    */

  sealed trait User {
    def id: UserId
    def name: UserName
  }
  object User {
    case class CommonUser(id: UserId, name: UserName) extends User

    case class UserWithPassword(
      id: UserId,
      name: UserName,
      password: EncryptedPassword
    ) extends User
  }

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
