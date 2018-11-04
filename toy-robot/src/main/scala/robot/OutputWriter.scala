package robot

trait OutputWriter[F[_]] {
  def writeLine(line: String): F[Unit]
}
