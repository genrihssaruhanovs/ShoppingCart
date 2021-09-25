package com.genrihssaruhanovs.shopingcart.model.endpoints
import com.genrihssaruhanovs.shopingcart.model.Common.{Payment, PaymentId}

trait PaymentClient[F[_]] {
  def process(payment: Payment): F[PaymentId]
}
