package com.genrihssaruhanovs.shopingcart.api
import squants.market.Money

import java.util.UUID
import scala.util.matching.Regex

object Common {
  type ErrorMessage = String

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
  case class CardHoldersName private (value: String) extends AnyVal
  object CardHoldersName {
    def of(value: String): Either[ErrorMessage, CardHoldersName] = {
      val regex: Regex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$".r
      Either.cond(regex.matches(value), CardHoldersName(value), "Wrong card holders name")
    }
  }
  case class CardNumber private (value: String) extends AnyVal
  object CardNumber {
    def of(value: String): Either[ErrorMessage, CardNumber] = {
      val regex: Regex = "[/d]{16}$".r
      Either.cond(regex.matches(value), CardNumber(value), "Wrong card number name")
    }
  }
  case class CardExpiration private (value: String) extends AnyVal
  object CardExpiration {
    def of(value: String): Either[ErrorMessage, CardExpiration] = {
      val regex: Regex = "[0-9]{2}/[0-9]{2}$".r
      Either.cond(regex.matches(value), CardExpiration(value), "Wrong card number name")
    }
  }
  case class CardCvv private (value: String) extends AnyVal
  object CardCvv {
    def of(value: String): Either[ErrorMessage, CardCvv] = {
      val regex: Regex = "[/d]{3}$".r
      Either.cond(regex.matches(value), CardCvv(value), "Wrong card number name")
    }
  }

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
