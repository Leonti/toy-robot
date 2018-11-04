package robot.io

trait CommandReader[F[_]] {
  def readRawCommands: F[String]
}
