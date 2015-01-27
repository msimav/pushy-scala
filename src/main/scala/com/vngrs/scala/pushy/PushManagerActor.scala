package com.vngrs.scala.pushy

import akka.actor._
import scala.util._
import javax.net.ssl.SSLContext
import com.relayrides.pushy.apns.ApnsEnvironment


class PushManagerActor(pushManager: PushManager) extends Actor with ActorLogging {
  import PushManagerActor._

  override def preStart(): Unit = {
    log.debug("PushManagerActor starting")
    if(!pushManager.isStarted) pushManager.start()
    log.debug("PushManagerActor started {}", pushManager.isStarted)
  }

  def receive: Receive = {
    case msg: PushNotification =>
      log.debug("PushManagerActor received {}", msg)
      pushManager.send(msg) match {
        case Success(_)  =>
          log.debug("PushManagerActor send successfully {}", msg)
          sender ! PushManagerSuccess(msg)
        case Failure(ex) =>
          log.debug("PushManagerActor failed to send {}", msg)
          sender ! PushManagerFailure(ex)
      }

  }

  override def postStop(): Unit = {
    log.debug("PushManagerActor stopping")
    pushManager.shutdown()
    log.debug("PushManagerActor stopped {}", pushManager.isShutDown)
  }

}

object PushManagerActor {

  sealed trait PushManagerMessage
  case class PushManagerSuccess(notification: PushNotification) extends PushManagerMessage
  case class PushManagerFailure(exception: Throwable) extends PushManagerMessage

  def props(pushManager: PushManager): Props =
    Props(classOf[PushManagerActor], pushManager)

  def props(name: String, environment: ApnsEnvironment, ssl: SSLContext): Props =
    props(PushManager(name, environment, ssl))

}
