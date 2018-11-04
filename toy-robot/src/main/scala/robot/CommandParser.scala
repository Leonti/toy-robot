package robot
import cats.data.ValidatedNel
import cats.implicits._
import robot.Robot._

object CommandParser {
  case class ParseError(value: String) extends AnyVal

  private val place = raw"PLACE (\d),(\d),([A-Z]+)".r

  private def parseFacing(asString: String): Either[ParseError, Facing] = asString match {
    case "NORTH" => Right(North)
    case "EAST"  => Right(East)
    case "SOUTH" => Right(South)
    case "WEST"  => Right(West)
    case _       => Left(ParseError(s"""Could not parse facing "$asString""""))
  }

  private def parseCommand(asString: String): Either[ParseError, Command] = asString match {
    case place(x, y, facing) => parseFacing(facing).map(f => Place(x.toInt, y.toInt, f))
    case "MOVE"              => Right(Move)
    case "LEFT"              => Right(TurnLeft)
    case "RIGHT"             => Right(TurnRight)
    case "REPORT"            => Right(Report)
    case _                   => Left(ParseError(s"""Could not parse command "$asString""""))
  }

  def parseCommands(raw: String): Either[List[ParseError], List[Command]] = {
    def parse(r: String): ValidatedNel[ParseError, Command] = parseCommand(r).toValidatedNel[ParseError]

    raw.split("\n").map(_.trim).filterNot(_.isEmpty).toList.traverse(parse).toEither.left.map(_.toList)
  }
}
