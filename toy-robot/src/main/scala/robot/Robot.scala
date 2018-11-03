package robot

import cats.data.State
import cats.implicits._

object Robot {

  sealed trait Facing
  final case object North extends Facing
  final case object East  extends Facing
  final case object South extends Facing
  final case object West  extends Facing

  sealed trait Command
  final case class Place(x: Int, y: Int, f: Facing) extends Command
  final case object Move                            extends Command
  final case object Left                            extends Command
  final case object Right                           extends Command
  final case object Report                          extends Command

  sealed trait Position
  final case object NotPlaced                  extends Position
  case class OnGrid(x: Int, y: Int, f: Facing) extends Position

  sealed trait Op
  final case object NoOp extends Op
  final case class ReportOp(p: Position) extends Op

  type App = State[Position, Op]

  private def processCommand(size: Int)(command: Command): App = State { position =>
    command match {
      case Place(x, y, f) if x >= 0 && x < size && y >= 0 && y < size => (OnGrid(x, y, f), NoOp)
      case Move =>
        position match {
          case OnGrid(x, y, f) =>
            f match {
              case North if y + 1 < size => (OnGrid(x, y + 1, f), NoOp)
              case East if x + 1 < size => (OnGrid(x + 1, y, f), NoOp)
              case South if y - 1 >= 0 => (OnGrid(x, y - 1, f), NoOp)
              case West if x - 1 >= 0 => (OnGrid(x - 1, y, f), NoOp)
              case _ => (position, NoOp)
            }
          case NotPlaced => (position, NoOp)
        }
      case Report => (position, ReportOp(position))
      case _ => (position, NoOp)
    }
  }

  case class Result(pos: Position, ops: List[Op])

  def move(size: Int, commands: List[Command]): Result = Result.tupled(commands.traverse(processCommand(size)).run(NotPlaced).value)
}
