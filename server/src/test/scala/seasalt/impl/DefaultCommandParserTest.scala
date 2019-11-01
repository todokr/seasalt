package seasalt.impl

import org.scalatest.{EitherValues, Matchers, WordSpec}
import seasalt.{Get, Key, Str, Set}

class DefaultCommandParserTest extends WordSpec with Matchers with EitherValues {

  val parser = new DefaultCommandParser

  "DefaultCommandParser" when {

    "get command" should {
       "return Get in Right" in {
         val command = """get x"""
         val actual = parser.parseCommand(command)
         actual.right.value shouldBe Get(Key("x"))
       }
    }

    "set command" should {
      "return Set in Right" in {
        val command = """set x "abc""""
        val actual = parser.parseCommand(command)
        actual.right.value shouldBe Set(Key("x"), Str("abc"))
      }

  }





  }
}
