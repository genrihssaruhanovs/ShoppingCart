package com.genrihssaruhanovs.shopingcart.json
import java.util.UUID

import cats.syntax.either._
import com.genrihssaruhanovs.shopingcart.database.HealthCheck
import com.genrihssaruhanovs.shopingcart.domain._
import com.genrihssaruhanovs.shopingcart.domain.Common._
import com.genrihssaruhanovs.shopingcart.domain.User.CreateUser
import dev.profunktor.auth.jwt.JwtToken
import io.circe._
import io.circe.generic.semiauto._
import squants.market.{Money, USD}

object ProtocolCodecs {
  implicit val paymentIdCodec: Codec[Payment.Id] = deriveCodec
  implicit val orderIdCodec: Codec[Order.Id] = deriveCodec
  implicit val brandIdCodec: Codec[Brand.Id] = deriveCodec
  implicit val categoryIdCodec: Codec[Category.Id] = deriveCodec
  implicit val itemIdCodec: Codec[Item.Id] = deriveCodec
  implicit val userIdCodec: Codec[User.Id] = deriveCodec
  implicit val userNameCodec: Codec[User.Name] = deriveCodec

  implicit val brandNameCodec: Codec[Brand.Name] = deriveCodec
  implicit val brandCodec: Codec[Brand] = deriveCodec

  implicit val categoryNameCodec: Codec[Category.Name] = deriveCodec
  implicit val categoryCodec: Codec[Category] = deriveCodec

  implicit val itemNameCodec: Codec[Item.Name] = deriveCodec
  implicit val itemDescriptionCodec: Codec[Item.Description] = deriveCodec
  implicit val moneyDecoder: Decoder[Money] = Decoder[BigDecimal].map(USD.apply)
  implicit val moneyEncoder: Encoder[Money] = Encoder[BigDecimal].contramap(_.amount)
  implicit val itemCodec: Codec[Item] = deriveCodec

  implicit val healthCheckStatusEncoder: Encoder[HealthCheck.Status] = deriveEncoder
  implicit val redisStatusEncoder: Encoder[HealthCheck.RedisStatus] = deriveEncoder
  implicit val postgresStatusEncoder: Encoder[HealthCheck.PostgresStatus] = deriveEncoder
  implicit val appStatusEncoder: Encoder[HealthCheck.AppStatus] = deriveEncoder

  implicit val jwtCodec: Codec[JwtToken] = deriveCodec
  implicit val userCodec: Codec[User] = deriveCodec
  implicit val createUserCodec: Codec[CreateUser] = deriveCodec

  implicit val quantityCodec: Codec[Quantity] = deriveCodec
  implicit val itemKeyDecoder: KeyDecoder[Item.Id] =
    (key: String) => Either.catchNonFatal(Item.Id(UUID.fromString(key))).toOption
  implicit val itemKeyEncoder: KeyEncoder[Item.Id] = (key: Item.Id) => key.toString

  implicit val cartCodec: Codec[Cart] = deriveCodec
  implicit val cartTotalCodec: Codec[Cart.Total] = deriveCodec

  implicit val orderCodec: Codec[Order] = deriveCodec

  implicit val cardHoldersNameDecoder: Decoder[Card.HoldersName] = deriveCodec
  implicit val cardNumberDecoder: Decoder[Card.Number] = deriveCodec
  implicit val cardExpirationDecoder: Decoder[Card.Expiration] = deriveCodec
  implicit val cardCvvDecoder: Decoder[Card.Cvv] = deriveCodec
  implicit val cardDecoder: Decoder[Card] = deriveDecoder
}
