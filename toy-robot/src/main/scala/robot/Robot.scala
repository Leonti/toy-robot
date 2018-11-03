package robot

object Robot {

  sealed trait Facing
  final case object North extends Facing
  final case object East extends Facing
  final case object South extends Facing
  final case object West extends Facing

  sealed trait Command
  final case class Place(x: Int, y: Int, f: Facing) extends Command
  final case object Move extends Command
  final case object Left extends Command
  final case object Right extends Command
  final case object Report extends Command

  sealed trait Position
  final case object NotPlaced extends Position
  case class OnTable(x: Int, y: Int, f: Facing) extends Position


  def move(size: Int, commands: List[Command]): Position = NotPlaced
}
