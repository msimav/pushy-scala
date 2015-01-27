package com.vngrs.scala.pushy

import java.util.concurrent.{BlockingQueue, ExecutorService}
import javax.net.ssl.SSLContext

import com.relayrides.pushy.apns.util.{ SimpleApnsPushNotification => SimpleNotification }
import com.relayrides.pushy.apns.{PushManager => JavaPushManager, PushManagerConfiguration, ApnsEnvironment}
import io.netty.channel.nio.NioEventLoopGroup

import scala.util.Try

case class PushManager(
  name: String,
  environment: ApnsEnvironment,
  sslContext: SSLContext,
  eventLoopGroup: Option[NioEventLoopGroup] = None,
  listenerExecutorService: Option[ExecutorService] = None,
  queue: Option[BlockingQueue[SimpleNotification]] = None,
  configuration: PushManagerConfiguration = new PushManagerConfiguration()
) {

  val manager = new JavaPushManager[SimpleNotification](
    environment, sslContext, eventLoopGroup.orNull, listenerExecutorService.orNull, queue.orNull, configuration, name)

  def start() = manager.start()
  def shutdown() = manager.shutdown()

  def isStarted: Boolean = manager.isStarted
  def isShutDown: Boolean = manager.isShutDown

  def send(notification: PushNotification): Try[Unit] =
    for {
      simpleNotification <- notification.toNotification
      result             <- Try(manager.getQueue.add(simpleNotification))
      if result
    } yield ()

}

object PushManager {

  def sandbox(name: String, sslContext: SSLContext) =
    PushManager(name, ApnsEnvironment.getSandboxEnvironment, sslContext)

  def production(name: String, sslContext: SSLContext) =
    PushManager(name, ApnsEnvironment.getProductionEnvironment, sslContext)

}
