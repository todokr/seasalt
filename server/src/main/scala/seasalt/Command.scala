package seasalt

sealed trait Command
final case class Get(key: Key) extends Command
final case class Set(key: Key, value: Value) extends Command
final case class Delete(key: Key) extends Command
