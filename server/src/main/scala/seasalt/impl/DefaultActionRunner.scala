package seasalt.impl

import seasalt._

class DefaultActionRunner extends ActionRunner {

  private val _store: collection.mutable.Map[Key, Value] = collection.mutable.Map.empty

  override def run(action: IOAction): Either[HandlerError, Result] = action match {
    case GetAction(key) =>
      _store.get(key)
        .map(value => Right(SingleResult(key, value)))
        .getOrElse(Right(NotFound(key)))

    case SetAction(key, value) =>
      _store += key -> value
      Right(SingleResult(key, value))

    case DeleteAction(key) =>
      _store.get(key).map { value =>
        _store -= key
        Right(SingleResult(key, value))
      }.getOrElse(Right(NotFound(key)))
  }
}
