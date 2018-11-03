package robot.parser

trait CommandReader[F[_]] {
  def readRawCommands: F[String]
}
