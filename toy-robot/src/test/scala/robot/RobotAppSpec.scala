package robot
import org.scalatest.{FlatSpec, Matchers}
import robot.effects.StateCommandWriter.TestProgram
import robot.effects.{StateCommandWriter, StaticCommandReader}

class RobotAppSpec extends FlatSpec with Matchers {

  private def runProgram(commands: String): List[String] = {
    val app = new RobotApp[TestProgram](new StaticCommandReader(commands), new StateCommandWriter())
    val (printed, _) = app.runRobot().run(List()).value
    printed
  }

  it should "print it's position" in {
    runProgram("PLACE 0,0,NORTH\nLEFT\nREPORT") shouldBe List("0,0,WEST")
  }

  it should "print error if robot is not on the table" in {
    runProgram("REPORT") shouldBe List("Robot is not on the grid!")
  }

}
