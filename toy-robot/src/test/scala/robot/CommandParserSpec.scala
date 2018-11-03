package robot
import org.scalatest.{FlatSpec, Matchers}
import robot.Robot._
import robot.parser.CommandParser

class CommandParserSpec extends FlatSpec with Matchers {

  private def parser(result: String) = new CommandParser(new StaticCommandReader(result))

  behavior of "command parser"

  it should "return empty result on empty input" in {
    parser("").commands() shouldBe Right(List())
  }

  it should "parse Place command" in {
    parser("PLACE 0,1,NORTH").commands() shouldBe Right(List(Place(0, 1, North)))
  }

}
