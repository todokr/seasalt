package seasalt.impl

import seasalt.{ErrorTranslator, FailedToResolve, FailedToStore, HandlerError, UnableToParseCommand}

class DefaultErrorTranslator extends ErrorTranslator {

  override def translate(handlerError: HandlerError): String = handlerError match {

    case UnableToParseCommand(rawCommand) => s"failed to parse: $rawCommand"
    case FailedToStore(msg) => s"failed to store: $msg"
    case FailedToResolve(msg) =>s"failed to resolve: $msg"
  }
}
