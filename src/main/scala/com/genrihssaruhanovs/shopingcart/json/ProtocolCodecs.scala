package com.genrihssaruhanovs.shopingcart.json
import com.genrihssaruhanovs.shopingcart.api.Common._
import com.genrihssaruhanovs.shopingcart.db.HealthCheck
import io.circe.{Codec, Decoder, DecodingFailure, Encoder, KeyDecoder, KeyEncoder}
import io.circe.generic.semiauto._
import squants.market.{Money, USD}

import java.util.UUID
import cats.syntax.either._

object ProtocolCodecs {
  implicit val paymentIdCodec: Codec[PaymentId] = deriveCodec
  implicit val orderIdCodec: Codec[OrderId] = deriveCodec
  implicit val brandIdCodec: Codec[BrandId] = deriveCodec
  implicit val categoryIdCodec: Codec[CategoryId] = deriveCodec
  implicit val itemIdCodec: Codec[ItemId] = deriveCodec
  implicit val userIdCodec: Codec[UserId] = deriveCodec

  implicit val brandNameCodec: Codec[BrandName] = deriveCodec
  implicit val brandCodec: Codec[Brand] = deriveCodec

  implicit val categoryNameCodec: Codec[CategoryName] = deriveCodec
  implicit val categoryCodec: Codec[Category] = deriveCodec

  implicit val itemNameCodec: Codec[ItemName] = deriveCodec
  implicit val itemDescriptionCodec: Codec[ItemDescription] = deriveCodec
  implicit val moneyDecoder: Decoder[Money] = Decoder[BigDecimal].map(USD.apply)
  implicit val moneyEncoder: Encoder[Money] = Encoder[BigDecimal].contramap(_.amount)
  implicit val itemCodec: Codec[Item] = deriveCodec

  implicit val healthCheckStatusEncoder: Encoder[HealthCheck.Status] = deriveEncoder
  implicit val redisStatusEncoder: Encoder[HealthCheck.RedisStatus] = deriveEncoder
  implicit val postgresStatusEncoder: Encoder[HealthCheck.PostgresStatus] = deriveEncoder
  implicit val appStatusEncoder: Encoder[HealthCheck.AppStatus] = deriveEncoder

  implicit val userNameCodec: Codec[UserName] = deriveCodec
  implicit val encryptedPasswordCodec: Codec[EncryptedPassword] = deriveCodec
  implicit val userCodec: Codec[User] = deriveCodec

  implicit val quantityCodec: Codec[Quantity] = deriveCodec
  implicit val itemKeyDecoder: KeyDecoder[ItemId] =
    (key: String) => Either.catchNonFatal(ItemId(UUID.fromString(key))).toOption
  implicit val itemKeyEncoder: KeyEncoder[ItemId] = (key: ItemId) => key.toString

  implicit val cartDecoder: Decoder[Cart] = deriveDecoder
  implicit val cartEncoder: Encoder[Cart] = deriveEncoder

  implicit val orderCodec: Codec[Order] = deriveCodec

  implicit val cardHoldersNameDecoder: Decoder[CardHoldersName] = Decoder.decodeString.emap(CardHoldersName.of)
  implicit val cardNumberDecoder: Decoder[CardNumber] = Decoder.decodeString.emap(CardNumber.of)
  implicit val cardExpirationDecoder: Decoder[CardExpiration] = Decoder.decodeString.emap(CardExpiration.of)
  implicit val cardCvvDecoder: Decoder[CardCvv] = Decoder.decodeString.emap(CardCvv.of)

  implicit val cardDecoder: Decoder[Card] = deriveDecoder

}
