package com.genrihssaruhanovs.shopingcart.json
import io.circe._
import io.estatico.newtype.Coercible

trait CoercibleCodecs {

  implicit def coercibleEncoder[R, N](
    implicit
    ev: Coercible[Encoder[R], Encoder[N]],
    R: Encoder[R]
  ): Encoder[N] = ev(R)

  implicit def coercibleDecoder[R, N](
    implicit
    ev: Coercible[Decoder[R], Decoder[N]],
    R: Decoder[R]
  ): Decoder[N] = ev(R)
}
