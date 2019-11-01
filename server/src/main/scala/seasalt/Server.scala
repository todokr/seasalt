package seasalt

import java.net.InetSocketAddress

import scala.concurrent.Await
import scala.concurrent.duration.Duration

import akka.io.Tcp._
import akka.io.{IO, Tcp}
import akka.{actor => classic}
import seasalt.impl.{DefaultActionBuilder, DefaultActionRunner, DefaultCommandParser, DefaultErrorTranslator}

class Server extends classic.Actor with classic.ActorLogging {
  import Server._
  import context.system

  override def receive: Receive = {
    case Start(port) =>
      IO(Tcp) ! Bind(self, new InetSocketAddress(port))
    case bound @ Bound(localAddress) =>
      log.info("SeaSalt start listening. host={}, port={}", localAddress.getHostName, localAddress.getPort)
      context.parent ! bound

    case CommandFailed(_: Bind) => context.stop(self)

    case Connected(remote, _) =>
      log.info("Connection established. host={}, port={}", remote.getHostName, remote.getPort)
      val commandContext = CommandContext(remote)
      val handlerRef = CommandHandler.props(parser, actionBuilder, actionRunner, errorTranslator, commandContext)
      val handler = context.actorOf(handlerRef, "handler")
      val connection = sender()
      connection ! Register(handler)
  }
}
object Server {

  final case class Start(port: Int) extends Event

  val parser = new DefaultCommandParser
  val actionBuilder = new DefaultActionBuilder
  val actionRunner = new DefaultActionRunner
  val errorTranslator = new DefaultErrorTranslator

  def props: classic.Props = classic.Props[Server]
}

object Main extends App {
  val system: classic.ActorSystem = classic.ActorSystem("seasalt")
  val server: classic.ActorRef = system.actorOf(Server.props, "server")

  server ! Server.Start(3001)

  Await.result(system.whenTerminated, Duration.Inf)
}