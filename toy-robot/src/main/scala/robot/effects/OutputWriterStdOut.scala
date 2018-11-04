package robot.effects
import cats.effect.IO
import robot.io.OutputWriter
class OutputWriterStdOut extends OutputWriter[IO] {
  override def writeLine(line: String): IO[Unit] = IO(println(line))
}
