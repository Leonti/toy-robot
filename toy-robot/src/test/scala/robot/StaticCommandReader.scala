package robot
import cats.Id
import robot.parser.CommandReader

class StaticCommandReader(result: String) extends CommandReader[Id] {
  override def readRawCommands: Id[String] = result
}
