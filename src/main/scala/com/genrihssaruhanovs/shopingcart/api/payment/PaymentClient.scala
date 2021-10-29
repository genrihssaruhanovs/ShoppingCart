package com.genrihssaruhanovs.shopingcart.api.payment
import com.genrihssaruhanovs.shopingcart.api.Common.{Payment, PaymentId}

trait PaymentClient[F[_]] {
  def process(payment: Payment): F[PaymentId]
}
