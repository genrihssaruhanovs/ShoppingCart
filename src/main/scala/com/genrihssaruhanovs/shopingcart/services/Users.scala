package com.genrihssaruhanovs.shopingcart.services
import com.genrihssaruhanovs.shopingcart.domain.User

trait Users[F[_]] {
  def find(
    userName: User.Name
  ): F[Option[User]]

  def create(
    userName: User.Name,
    password: User.EncryptedPassword
  ): F[User.Id]
}
