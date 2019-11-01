package seasalt

final case class Key(underlining: String)

sealed trait Value
final case class Str(value: String)extends Value
final case class Num(value: Int) extends Value
