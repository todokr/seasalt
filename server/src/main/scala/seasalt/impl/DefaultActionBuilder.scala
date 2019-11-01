package seasalt.impl

import seasalt._

class DefaultActionBuilder extends ActionBuilder {

  override def buildAction(command: Command): IOAction = command match {
    case Get(key) => GetAction(key)
    case Set(key, value) => SetAction(key, value)
    case Delete(key) => DeleteAction(key)
  }
}
