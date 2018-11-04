package robot

import cats.Monad
import cats.implicits._
import robot.Robot._
import robot.parser.CommandReader
import robot.parser.CommandParser.parseCommands

class RobotApp[F[_]: Monad](commandReader: CommandReader[F], outputWriter: OutputWriter[F]) {

  private def formatFacing(facing: Facing): String = facing match {
    case North => "NORTH"
    case East  => "EAST"
    case South => "SOUTH"
    case West  => "WEST"
  }

  private def printOp(op: Op): F[Unit] = op match {
    case ReportOp(OnGrid(x, y, facing)) => outputWriter.writeLine(s"$x,$y,${formatFacing(facing)}")
    case ReportOp(NotPlaced)            => outputWriter.writeLine("Robot is not on the table!")
    case NoOp                           => Monad[F].pure(())
  }

  def runRobot(): F[Unit] =
    for {
      rawCommands <- commandReader.readRawCommands
      _ <- parseCommands(rawCommands) match {
        case Left(errors)    => errors.map(_.value).traverse(outputWriter.writeLine)
        case Right(commands) => Robot.move(5, commands).ops.traverse(printOp)
      }
    } yield ()
}
