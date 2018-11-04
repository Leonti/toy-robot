package robot.effects
import cats.effect.IO
import robot.parser.CommandReader

class CommandReaderStdIn extends CommandReader[IO] {
  override def readRawCommands: IO[String] = IO(io.Source.stdin.mkString)
}
