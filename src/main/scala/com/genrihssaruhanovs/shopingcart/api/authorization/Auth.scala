package com.genrihssaruhanovs.shopingcart.api.authorization
import com.genrihssaruhanovs.shopingcart.api.Common.{JwtToken, Password, User, UserName}

trait Auth[F[_]] {
  def findUser(token: JwtToken): F[Option[User]]

  def newUser(username: UserName, password: Password): F[JwtToken]

  def login(userName: UserName, password: Password): F[JwtToken]

  def logout(token: JwtToken, userName: UserName): F[Unit]
}
