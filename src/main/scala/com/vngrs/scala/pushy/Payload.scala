package com.vngrs.scala.pushy

import com.relayrides.pushy.apns.util.ApnsPayloadBuilder

import scala.util.Try


case class LocalizedAlert(key: String, args: Array[String])

case class Payload(
  alertBody: Option[String] = None,
  localizedAlert: Option[LocalizedAlert] = None,
  launchImage: Option[String] = None,
  showActionButton: Boolean = true,
  localizedActionButtonKey: Option[String] = None,
  badgeNumber: Option[Integer] = None,
  categoryName: Option[String] = None,
  soundFileName: Option[String] = None,
  contentAvailable: Boolean = false
) {

  def build: Try[String] = Try {
    new ApnsPayloadBuilder()
      .setAlertBody(this.alertBody.orNull)
      .setLocalizedAlertMessage(this.localizedAlert.map(_.key).orNull, this.localizedAlert.map(_.args).orNull)
      .setLaunchImage(this.launchImage.orNull)
      .setShowActionButton(this.showActionButton)
      .setLocalizedActionButtonKey(this.localizedActionButtonKey.orNull)
      .setBadgeNumber(this.badgeNumber.orNull)
      .setCategoryName(this.categoryName.orNull)
      .setSoundFileName(this.soundFileName.orNull)
      .setContentAvailable(this.contentAvailable)
      .buildWithDefaultMaximumLength()
  }

}
