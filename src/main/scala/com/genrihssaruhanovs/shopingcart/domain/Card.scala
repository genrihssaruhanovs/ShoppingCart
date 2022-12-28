package com.genrihssaruhanovs.shopingcart.domain
import com.genrihssaruhanovs.shopingcart.domain.Card._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.boolean.And
import eu.timepit.refined.collection.Size
import eu.timepit.refined.string.{MatchesRegex, ValidInt}
import io.estatico.newtype.macros.newtype

case class Card(
  name: HoldersName,
  number: Number,
  expiration: Expiration,
  cvv: Cvv
)

object Card {
  type Rgx = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"
  type NamePred = String Refined MatchesRegex[Rgx]
  type NumberPred = Long Refined Size[16]
  type ExpirationPred = String Refined (Size[4] And ValidInt)
  type CVVPred = Int Refined Size[3]

  @newtype case class HoldersName private (value: NamePred)
  @newtype case class Number(value: NumberPred)
  @newtype case class Expiration(value: ExpirationPred)
  @newtype case class Cvv(value: CVVPred)
}
