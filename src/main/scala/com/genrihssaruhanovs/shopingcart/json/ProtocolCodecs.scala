package com.genrihssaruhanovs.shopingcart.json
import com.genrihssaruhanovs.shopingcart.api.Common.{Brand, Category, Item}
import com.genrihssaruhanovs.shopingcart.db.HealthCheck
import io.circe.{Codec, Decoder, Encoder}
import io.circe.generic.semiauto._
import squants.market.{Money, USD}

object ProtocolCodecs extends CoercibleCodecs {

  implicit val brandCodec: Codec[Brand] = deriveCodec[Brand]
  implicit val categoryCodec: Codec[Category] = deriveCodec[Category]

  implicit val currencyCodec: Decoder[Money] = Decoder[BigDecimal].map(USD.apply)
  implicit val moneyEncoder: Encoder[Money] = Encoder[BigDecimal].contramap(_.amount)

  implicit val itemCodec: Codec[Item] = deriveCodec[Item]

  implicit val healthCheckStatusEncoder: Encoder[HealthCheck.Status] = deriveEncoder[HealthCheck.Status]
  implicit val appStatusEncoder: Encoder[HealthCheck.AppStatus] = deriveEncoder[HealthCheck.AppStatus]
}
