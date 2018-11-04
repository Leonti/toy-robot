package robot
import org.scalatest.{FlatSpec, Matchers}
import robot.Robot._
import robot.parser.CommandParser
import robot.parser.CommandParser.ParseError

class CommandParserSpec extends FlatSpec with Matchers {

  private def parser(result: String) = new CommandParser(new StaticCommandReader(result))

  behavior of "command parser"

  it should "return empty result on empty input" in {
    parser("").commands() shouldBe Right(List())
  }

  it should "parse Place command" in {
    parser("PLACE 0,1,NORTH").commands() shouldBe Right(List(Place(0, 1, North)))
  }

  it should "fail if can't parse facing" in {
    parser("PLACE 0,1,UNKNOWN").commands() shouldBe Left(List(ParseError("""Could not parse facing "UNKNOWN"""")))
  }

  it should "parse Move command" in {
    parser("MOVE").commands() shouldBe Right(List(Move))
  }

  it should "parse TurnLeft command" in {
    parser("LEFT").commands() shouldBe Right(List(TurnLeft))
  }

  it should "parse TurnRight command" in {
    parser("RIGHT").commands() shouldBe Right(List(TurnRight))
  }

  it should "parse Report command" in {
    parser("REPORT").commands() shouldBe Right(List(Report))
  }

  it should "parse multiple commands" in {
    parser("LEFT\nREPORT").commands() shouldBe Right(List(TurnLeft, Report))
  }

  it should "collect errors" in {
    parser("LEFTio\nREPORTio").commands() shouldBe Left(List(
      ParseError(s"""Could not parse command "LEFTio""""),
      ParseError(s"""Could not parse command "REPORTio"""")
    ))
  }
}
