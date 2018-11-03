import org.scalatest.{FlatSpec, Matchers}
import robot.Robot
import robot.Robot._

class RobotSpec extends FlatSpec with Matchers {

  behavior of "robot"

  it should "have unknown position if not placed" in {
   Robot.move(5, List()) shouldBe NotPlaced
  }
}
