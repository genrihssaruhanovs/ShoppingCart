package com.genrihssaruhanovs.shopingcart.utility
import scala.util.control.NoStackTrace

trait Error

object Error {
  case object EmptyCartError extends NoStackTrace
  case class PaymentError(cause: String) extends NoStackTrace
  case class OrderError(cause: String) extends NoStackTrace
}
