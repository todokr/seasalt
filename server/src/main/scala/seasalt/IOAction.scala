package seasalt

sealed trait IOAction
final case class GetAction(key: Key) extends IOAction
final case class SetAction(key: Key, value: Value) extends IOAction
final case class DeleteAction(key: Key) extends IOAction
