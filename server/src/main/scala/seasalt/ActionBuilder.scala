package seasalt

trait ActionBuilder {
  def buildAction(command: Command): IOAction
}
