package seasalt

trait ErrorTranslator {

  def translate(handlerError: HandlerError): String
}
