package seasalt

import java.nio.charset.StandardCharsets

import akka.io.Tcp.{PeerClosed, Received, Write}
import akka.util.ByteString
import akka.{actor => classic}

class CommandHandler(
  parser: CommandParser,
  actionBuilder: ActionBuilder,
  runner: ActionRunner,
  errorTranslator: ErrorTranslator,
  cc: CommandContext
) extends classic.Actor with classic.ActorLogging {

  def receive = {

    case Received(data) =>
      val rawCommand = data.decodeString(StandardCharsets.UTF_8).trim

      val result = for {
        command <- parser.parseCommand(rawCommand)
        action = actionBuilder.buildAction(command)
        result <- runner.run(action)
      } yield result

      result match {
        case Left(handlerError) =>
          val errorMsg = errorTranslator.translate(handlerError)
          sender() ! Write(ByteString(errorMsg, StandardCharsets.UTF_8))

        case Right(value) =>
          sender() ! Write(ByteString(value.serialize, StandardCharsets.UTF_8))
      }

    case PeerClosed =>
      log.info(
        "Connection closed. host={}, port={}",
        cc.remote.getHostName,
        cc.remote.getPort
      )
      context.stop(self)
  }
}

object CommandHandler {

  def props(
    parser: CommandParser,
    actionBuilder: ActionBuilder,
    runner: ActionRunner,
    errorTranslator: ErrorTranslator,
    cc: CommandContext
  ): classic.Props =
    classic.Props(new CommandHandler(parser, actionBuilder, runner, errorTranslator, cc))
}