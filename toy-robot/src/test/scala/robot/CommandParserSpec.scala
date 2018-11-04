package robot
import org.scalatest.{FlatSpec, Matchers}
import robot.Robot._
import CommandParser.{ParseError, parseCommands}

class CommandParserSpec extends FlatSpec with Matchers {

  behavior of "command parser"

  it should "return empty result on empty input" in {
    parseCommands("") shouldBe Right(List())
  }

  it should "parse Place command" in {
    parseCommands("PLACE 0,1,NORTH") shouldBe Right(List(Place(0, 1, North)))
  }

  it should "fail if can't parse facing" in {
    parseCommands("PLACE 0,1,UNKNOWN") shouldBe Left(List(ParseError("""Could not parse facing "UNKNOWN"""")))
  }

  it should "parse Move command" in {
    parseCommands("MOVE") shouldBe Right(List(Move))
  }

  it should "parse TurnLeft command" in {
    parseCommands("LEFT") shouldBe Right(List(TurnLeft))
  }

  it should "parse TurnRight command" in {
    parseCommands("RIGHT") shouldBe Right(List(TurnRight))
  }

  it should "parse Report command" in {
    parseCommands("REPORT") shouldBe Right(List(Report))
  }

  it should "parse multiple commands" in {
    parseCommands("LEFT\nREPORT") shouldBe Right(List(TurnLeft, Report))
  }

  it should "collect errors" in {
    parseCommands("LEFTio\nREPORTio") shouldBe Left(List(
      ParseError(s"""Could not parse command "LEFTio""""),
      ParseError(s"""Could not parse command "REPORTio"""")
    ))
  }
}
