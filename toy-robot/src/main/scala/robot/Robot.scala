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
  final case object TurnLeft                        extends Command
  final case object TurnRight                       extends Command
  final case object Report                          extends Command

  sealed trait Position
  final case object NotPlaced                  extends Position
  case class OnGrid(x: Int, y: Int, f: Facing) extends Position

  sealed trait Op
  final case object NoOp                 extends Op
  final case class ReportOp(p: Position) extends Op

  private def processCommand(size: Int)(command: Command): State[Position, Op] = State { position =>
    def onGrid(x: Int, y: Int) = x >= 0 && x < size && y >= 0 && y < size

    command match {
      case Place(x, y, f) => if (onGrid(x, y)) (OnGrid(x, y, f), NoOp) else (position, NoOp)
      case Move =>
        position match {
          case OnGrid(x, y, f) =>
            f match {
              case North if onGrid(x, y + 1) => (OnGrid(x, y + 1, f), NoOp)
              case East if onGrid(x + 1, y)  => (OnGrid(x + 1, y, f), NoOp)
              case South if onGrid(x, y - 1) => (OnGrid(x, y - 1, f), NoOp)
              case West if onGrid(x - 1, y)  => (OnGrid(x - 1, y, f), NoOp)
              case _                         => (position, NoOp)
            }
          case NotPlaced => (position, NoOp)
        }
      case TurnRight =>
        position match {
          case OnGrid(x, y, North) => (OnGrid(x, y, East), NoOp)
          case OnGrid(x, y, East)  => (OnGrid(x, y, South), NoOp)
          case OnGrid(x, y, South) => (OnGrid(x, y, West), NoOp)
          case OnGrid(x, y, West)  => (OnGrid(x, y, North), NoOp)
          case NotPlaced           => (position, NoOp)
        }
      case TurnLeft =>
        position match {
          case OnGrid(x, y, North) => (OnGrid(x, y, West), NoOp)
          case OnGrid(x, y, West)  => (OnGrid(x, y, South), NoOp)
          case OnGrid(x, y, South) => (OnGrid(x, y, East), NoOp)
          case OnGrid(x, y, East)  => (OnGrid(x, y, North), NoOp)
          case NotPlaced           => (position, NoOp)
        }
      case Report => (position, ReportOp(position))
    }
  }

  case class Result(pos: Position, ops: List[Op])

  def move(size: Int, commands: List[Command]): Result = Result.tupled(commands.traverse(processCommand(size)).run(NotPlaced).value)
}
