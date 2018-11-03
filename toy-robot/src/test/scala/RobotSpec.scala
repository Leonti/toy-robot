import org.scalatest.{FlatSpec, Matchers}
import robot.Robot
import robot.Robot._

class RobotSpec extends FlatSpec with Matchers {

  behavior of "robot"

  it should "have unknown position if not placed" in {
   Robot.move(5, List()) shouldBe NotPlaced
  }

  it should "have placed position" in {
    Robot.move(5, List(Place(0, 0, North))) shouldBe OnGrid(0, 0,North)
  }

}
