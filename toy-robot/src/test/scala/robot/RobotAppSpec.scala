package robot
import org.scalatest.{FlatSpec, Matchers}
import robot.StateCommandWriter.TestProgram

class RobotAppSpec extends FlatSpec with Matchers {

  it should "print it's position" in {
    val app = new RobotApp[TestProgram](new StaticCommandReader("PLACE 0,0,NORTH\nLEFT\nREPORT"), new StateCommandWriter())
    val (printed, _) = app.runRobot().run(List()).value
    printed shouldBe List("0,0,WEST")
  }

}
