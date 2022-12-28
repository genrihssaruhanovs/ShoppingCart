package com.genrihssaruhanovs.shopingcart.services
import com.genrihssaruhanovs.shopingcart.domain.User
import dev.profunktor.auth.jwt.JwtToken

trait Auth[F[_]] {
  def findUser(token: JwtToken): F[Option[User]]

  def newUser(username: User.Name, password: User.Password): F[JwtToken]

  def login(userName: User.Name, password: User.Password): F[JwtToken]

  def logout(token: JwtToken, userName: User.Name): F[Unit]
}
