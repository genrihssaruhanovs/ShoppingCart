package com.genrihssaruhanovs.shopingcart.model.endpoints
import com.genrihssaruhanovs.shopingcart.model.Common.{EncryptedPassword, UserId, UserName, UserWithPassword}

trait Users[F[_]] {
  def find(
    userName: UserName
  ): F[Option[UserWithPassword]]

  def create(
    userName: UserName,
    password: EncryptedPassword
  ): F[UserId]
}
