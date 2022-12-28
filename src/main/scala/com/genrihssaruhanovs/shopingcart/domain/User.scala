package com.genrihssaruhanovs.shopingcart.domain

import java.util.UUID

import com.genrihssaruhanovs.shopingcart.domain.User._
import eu.timepit.refined.types.string.NonEmptyString
import io.estatico.newtype.macros.newtype

case class User(
  id: Id,
  name: Name,
  password: Password
)

object User {
  @newtype case class Id(value: UUID)

  @newtype case class Name(value: NonEmptyString)

  @newtype case class Password(value: NonEmptyString)

  @newtype case class EncryptedPassword(value: String)

  case class CreateUser(
    name: Name,
    password: Password
  )

  @newtype case class Admin(value: User)
}
