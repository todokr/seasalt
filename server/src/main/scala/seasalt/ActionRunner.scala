package seasalt

trait ActionRunner {

  def run(action: IOAction): Either[HandlerError, Result]
}
