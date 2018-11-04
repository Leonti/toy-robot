package robot.effects

import cats.Monad
import robot.io.CommandReader
class StaticCommandReader[F[_]: Monad](result: String) extends CommandReader[F] {
  override def readRawCommands: F[String] = Monad[F].pure(result)
}
