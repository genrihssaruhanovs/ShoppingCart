package com.genrihssaruhanovs.shopingcart.utility.retries
import cats.effect.Temporal
import org.typelevel.log4cats.Logger
import retry.RetryDetails.{GivingUp, WillDelayAndRetry}
import retry.{RetryDetails, RetryPolicy, retryingOnAllErrors}

trait Retry[F[_]] {
  def retry[A](
    policy: RetryPolicy[F],
    retriable: Retriable
  )(
    fa: F[A]
  ): F[A]
}

object Retry {
  def apply[F[_] : Retry]: Retry[F] = implicitly

  implicit def forLoggerTemporal[F[_] : Logger : Temporal]: Retry[F] =
    new Retry[F] {
      def retry[A](
        policy: RetryPolicy[F],
        retriable: Retriable
      )(
        fa: F[A]
      ): F[A] = {
        def onError(
          e: Throwable,
          details: RetryDetails
        ): F[Unit] =
          details match {
            case WillDelayAndRetry(_, retriesSoFar, _) => Logger[F].error(
                s"Failed on $retriable because of ${ e.getMessage }. We retriad $retriesSoFar times." //TODO - retriable.show
              )
            case GivingUp(totalRetries, _) =>
              Logger[F].error(
                s"Giving up on $retriable because of ${ e.getMessage }. after $totalRetries retries." //TODO - retriable.show
              )
          }
        retryingOnAllErrors[A](policy, onError)(fa)
      }
    }
}
