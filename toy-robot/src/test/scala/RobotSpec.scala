import org.scalatest.{FlatSpec, Matchers}
import robot.Robot
import robot.Robot._

class RobotSpec extends FlatSpec with Matchers {

  behavior of "robot"

  it should "have unknown position if not placed" in {
   Robot.move(5, List()) shouldBe NotPlaced
  }

  it should "have placed position" in {
    Robot.move(5, List(Place(0, 0, North))) shouldBe OnGrid(0, 0, North)
  }

  it should "move North" in {
    Robot.move(5, List(Place(2, 2, North), Move)) shouldBe OnGrid(2, 3, North)
  }

  it should "move East" in {
    Robot.move(5, List(Place(2, 2, East), Move)) shouldBe OnGrid(3, 2, East)
  }

  it should "move South" in {
    Robot.move(5, List(Place(2, 2, South), Move)) shouldBe OnGrid(2, 1, South)
  }

  it should "move West" in {
    Robot.move(5, List(Place(2, 2, West), Move)) shouldBe OnGrid(1, 2, West)
  }

  it should "not move outside the grid when moving North" in {
    Robot.move(5, List(Place(0, 4, North), Move)) shouldBe OnGrid(0, 4, North)
  }

  it should "not move outside the grid when moving East" in {
    Robot.move(5, List(Place(4, 0, East), Move)) shouldBe OnGrid(4, 0, East)
  }

  it should "not move outside the grid when moving West" in {
    Robot.move(5, List(Place(0, 0, West), Move)) shouldBe OnGrid(0, 0, West)
  }

  it should "not move outside the grid when moving South" in {
    Robot.move(5, List(Place(0, 0, South), Move)) shouldBe OnGrid(0, 0, South)
  }
}
