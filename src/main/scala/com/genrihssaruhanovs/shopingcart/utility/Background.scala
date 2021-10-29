package com.genrihssaruhanovs.shopingcart.utility
import cats.effect.kernel.Temporal
import cats.effect.std.Supervisor
import cats.syntax.all._

import scala.concurrent.duration.FiniteDuration

sealed trait Background[F[_]] {
  def schedule[A](
    fa: F[A],
    duration: FiniteDuration
  ): F[Unit]
}

object Background {
  def apply[F[_] : Background]: Background[F] = implicitly

  implicit def backgroundInstance[F[_]](implicit s: Supervisor[F], t: Temporal[F]): Background[F] =
    new Background[F] {
      def schedule[A](fa: F[A], duration: FiniteDuration): F[Unit] =
        s.supervise(t.sleep(duration) *> fa).void
    }
}
