package com.vngrs.scala.pushy

import java.util.Date

import com.relayrides.pushy.apns.DeliveryPriority
import com.relayrides.pushy.apns.util.SimpleApnsPushNotification
import com.relayrides.pushy.apns.util.TokenUtil.tokenStringToByteArray

import scala.util.Try

case class PushNotification(
  token: String,
  payload: Payload,
  invalidationTime: Option[Date] = None,
  priority: DeliveryPriority = DeliveryPriority.IMMEDIATE) {

  def toNotification = for {
    tokenByte  <- Try(tokenStringToByteArray(token))
    payloadStr <- payload.build
  } yield new SimpleApnsPushNotification(tokenByte, payloadStr, invalidationTime.orNull, priority)

}

