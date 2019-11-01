package seasalt

sealed trait HandlerError
final case class UnableToParseCommand(rawCommand: String) extends HandlerError
final case class FailedToStore(msg: String) extends HandlerError
final case class FailedToResolve(msg: String) extends HandlerError
