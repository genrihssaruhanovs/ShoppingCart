package com.genrihssaruhanovs.shopingcart.http.routes.query
import cats.implicits.toBifunctorOps
import eu.timepit.refined.api.{Refined, Validate}
import eu.timepit.refined.refineV
import org.http4s.{ParseFailure, QueryParamDecoder}
import org.http4s.dsl.impl.OptionalQueryParamDecoderMatcher

object ParamMatcher {
  object Brand extends OptionalQueryParamDecoderMatcher[Param.Brand]("brand")

  implicit def refinedParamDecoder[T : QueryParamDecoder, P](
    implicit
    ev: Validate[T, P]
  ): QueryParamDecoder[T Refined P] =
    QueryParamDecoder[T].emap(
      refineV[P](_).leftMap(m => ParseFailure(m, m))
    )
}
