package com.genrihssaruhanovs.shopingcart.http.clients
import com.genrihssaruhanovs.shopingcart.domain.Payment

trait PaymentClient[F[_]] {
  def process(payment: Payment): F[Payment.Id]
}
