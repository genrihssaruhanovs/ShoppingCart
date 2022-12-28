package com.genrihssaruhanovs.shopingcart.utility
import com.genrihssaruhanovs.shopingcart.domain.User

import scala.util.control.NoStackTrace

trait Error

object Error {
  case object EmptyCartError extends NoStackTrace
  case class CartNotFound(userId: User.Id) extends NoStackTrace
  case class OrderOrPaymentError(cause: String) extends NoStackTrace
  case class UserNotFound(userId: User.Id) extends NoStackTrace
  case class InvalidPassword(cause: String) extends NoStackTrace
  case class UserNameInUse(userName: User.Name) extends NoStackTrace
}
