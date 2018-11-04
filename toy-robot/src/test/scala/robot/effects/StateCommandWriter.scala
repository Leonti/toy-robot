package robot.effects

import cats.data.State
import robot.effects.StateCommandWriter.TestProgram
import robot.io.OutputWriter

object StateCommandWriter {
  type TestProgram[A] = State[List[String], A]
}

class StateCommandWriter extends OutputWriter[TestProgram] {
  override def writeLine(line: String): TestProgram[Unit] = State(lines => (line :: lines, ()))
}
