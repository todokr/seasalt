package seasalt.impl

import scala.util.matching.Regex

import seasalt._

class DefaultCommandParser extends CommandParser {
  import DefaultCommandParser._

  override def parseCommand(command: String): Either[HandlerError, Command] =
    command.split(" ") match {
      case Array("get", rawKey) =>
        Right(Get(Key(rawKey)))
      case Array("set", rawKey, StrPattern(s)) =>
        Right(Set(Key(rawKey), Str(s)))
      case Array("set", rayKey, NumPattern(n)) =>
        Right(Set(Key(rayKey), Num(n.toInt)))
      case Array("delete", rawKey) =>
        Right(Delete(Key(rawKey)))
      case unknown =>
        Left(UnableToParseCommand(unknown.mkString(" ")))
    }
}

object DefaultCommandParser {
  val StrPattern: Regex = """^"(\w*)"$""".r
  val NumPattern: Regex = """^([0-9]+)$""".r
}