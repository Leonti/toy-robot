import cats.effect.IO
import robot.RobotApp
import robot.effects.{CommandReaderStdIn, OutputWriterStdOut}

object Main extends App {
  val app = new RobotApp[IO](new CommandReaderStdIn(), new OutputWriterStdOut())
  app.runRobot().unsafeRunSync()
}
