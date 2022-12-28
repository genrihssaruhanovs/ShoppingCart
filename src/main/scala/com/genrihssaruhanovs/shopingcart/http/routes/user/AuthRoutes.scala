package com.genrihssaruhanovs.shopingcart.http.routes.user

import cats.MonadThrow
import cats.implicits.{catsSyntaxApplicativeError, catsSyntaxApply, toFlatMapOps, toSemigroupKOps, toTraverseOps}
import com.genrihssaruhanovs.shopingcart.domain.User
import com.genrihssaruhanovs.shopingcart.domain.User.CreateUser
import com.genrihssaruhanovs.shopingcart.json.ProtocolCodecs.{createUserCodec, jwtCodec, userCodec, userNameCodec}
import com.genrihssaruhanovs.shopingcart.json.RefinedDecoder.RefinedRequestDecoder
import com.genrihssaruhanovs.shopingcart.services.Auth
import com.genrihssaruhanovs.shopingcart.utility.Error.{InvalidPassword, UserNameInUse, UserNotFound}
import dev.profunktor.auth.AuthHeaders
import org.http4s.{AuthedRoutes, HttpRoutes}
import org.http4s.circe.CirceEntityEncoder._
import org.http4s.circe.JsonDecoder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.{AuthMiddleware, Router}

final case class AuthRoutes[F[_] : JsonDecoder : MonadThrow](
  auth: Auth[F]
) extends Http4sDsl[F] {

  private[user] val prefixPath = "/auth"

  private val httpRoutes: HttpRoutes[F] = HttpRoutes.of[F] {
    case req @ POST -> Root / "login" =>
      req.decodeR[User] { user =>
        auth
          .login(user.name, user.password)
          .flatMap(Ok(_))
          .recoverWith {
            case UserNotFound(_) | InvalidPassword(_) =>
              Forbidden()
          }
      }
    case req @ POST -> Root / "create" =>
      req.decodeR[CreateUser] { user =>
        auth.newUser(
          user.name,
          user.password
        )
          .flatMap(Created(_))
          .recoverWith {
            case UserNameInUse(u) =>
              Conflict(u)
          }
      }
  }

  private val authedHttpRoutes: AuthedRoutes[User, F] =
    AuthedRoutes.of {
      case ar @ POST -> Root / "logout" as user =>
        AuthHeaders
          .getBearerToken(ar.req)
          .traverse(auth.logout(_, user.name)) *> NoContent()
    }

  def routes(authMiddleware: AuthMiddleware[F, User]): HttpRoutes[F] = Router(
    prefixPath -> (httpRoutes <+> authMiddleware(authedHttpRoutes))
  )
}
