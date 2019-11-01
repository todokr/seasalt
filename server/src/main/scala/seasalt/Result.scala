package seasalt

sealed trait Result {
  def serialize: String = toString + "\n"
}
final case class SingleResult(key: Key, value: Value) extends Result
case class NotFound(key: Key) extends Result