package com.genrihssaruhanovs.shopingcart.json
import cats.MonadThrow
import cats.implicits.{catsSyntaxApplicativeError, toFlatMapOps}
import io.circe.Decoder
import org.http4s.{Request, Response}
import org.http4s.circe.{JsonDecoder, toMessageSyntax}
import org.http4s.dsl.Http4sDsl

object RefinedDecoder {
  implicit class RefinedRequestDecoder[F[_] : JsonDecoder : MonadThrow](
    req: Request[F]
  ) extends Http4sDsl[F] {
    def decodeR[A : Decoder](
      f: A => F[Response[F]]
    ): F[Response[F]] =
      req.asJsonDecode[A].attempt.flatMap {
        case Left(e) =>
          Option(e.getCause) match {
            case Some(c) if c.getMessage.startsWith("Predicate") =>
              BadRequest(c.getMessage)
            case _ =>
              UnprocessableEntity()
          }
        case Right(a) => f(a)
      }
  }
}
