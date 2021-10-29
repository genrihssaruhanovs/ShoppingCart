package com.genrihssaruhanovs.shopingcart.api.user
import com.genrihssaruhanovs.shopingcart.api.Common.{EncryptedPassword, UserId, UserName, UserWithPassword}

trait Users[F[_]] {
  def find(
    userName: UserName
  ): F[Option[UserWithPassword]]

  def create(
    userName: UserName,
    password: EncryptedPassword
  ): F[UserId]
}
