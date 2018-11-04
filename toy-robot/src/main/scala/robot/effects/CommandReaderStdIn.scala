package robot.effects
import cats.effect.IO
import robot.io.CommandReader
class CommandReaderStdIn extends CommandReader[IO] {
  override def readRawCommands: IO[String] = IO(io.Source.stdin.mkString)
}
