package seasalt

trait CommandParser {

  def parseCommand(command: String): Either[HandlerError, Command]
}
