package robot.parser

import cats.Monad
import cats.implicits._
import robot.Robot._

object CommandParser {
  case class Error(value: String) extends AnyVal
}

class CommandParser[F[_]: Monad](cr: CommandReader[F]) {
  import CommandParser.Error
  private val place = raw"PLACE (\d),(\d),([A-Z]+)".r

  def parseFacing(asString: String): Either[Error, Facing] = asString match {
    case "NORTH" => Right(North)
    case "EAST"  => Right(East)
    case "SOUTH" => Right(South)
    case "WEST"  => Right(West)
    case _       => Left(Error(s"""Could not parse facing "$asString""""))
  }

  def parseCommand(asString: String): Either[Error, Command] = asString match {
    case place(x, y, facing) => parseFacing(facing).map(f => Place(x.toInt, y.toInt, f))
    case "MOVE"              => Right(Move)
    case "LEFT"              => Right(TurnLeft)
    case "RIGHT"             => Right(TurnRight)
    case "REPORT"            => Right(Report)
    case _                   => Left(Error(s"""Could not parse command "$asString""""))
  }

  def commands(): F[Either[Error, List[Command]]] =
    for {
      raw <- cr.readRawCommands
    } yield raw.split("\n").map(_.trim).filterNot(_.isEmpty).toList.traverse(parseCommand)

}
