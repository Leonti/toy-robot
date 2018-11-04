package robot.io

trait OutputWriter[F[_]] {
  def writeLine(line: String): F[Unit]
}
