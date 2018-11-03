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
  final case object NotPlaced                   extends Position
  case class OnGrid(x: Int, y: Int, f: Facing) extends Position

  type App = State[Position, Position]

  private def processCommand(size: Int)(command: Command): App = State { position =>
    val next = command match {
      case Place(x, y, f) => OnGrid(x, y, f)
      case _ => position
    }

    (next, next)
  }

  def move(size: Int, commands: List[Command]): Position = {
    val (position, _) = commands.traverse(processCommand(size)).run(NotPlaced).value

    position
  }
}
