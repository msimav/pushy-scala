# pushy-scala

pushy-scala is Scala wrapper around [pushy](https://github.com/relayrides/pushy),
a Java library for sending [APNs](http://developer.apple.com/library/mac/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/Introduction.html) (iOS and OS X) push notifications.

Example:
``` scala
import com.vngrs.scala.pushy._
import com.vngrs.scala.pushy.Implicits._

class Main extends App {

  val context = SSLContext("sandbox.p12", "").get
  val manager = PushManager.sandbox("Example", context)

  manager.start()

  val token = "<5f6aa01d 8e335894 9b7c25d4 61bb78ad 740f4707 462c7eaf bebcf74f a5ddb387>"
  val payload = Payload(alertBody = "Ring ring, Neo.", soundFileName = "ring-ring.aiff")
  val result = manager.send(PushNotification(token, payload))

  result match {
    case Success(_)  => println("Notification sent!")
    case Failure(ex) => println(s"Notification failed to sent because of ${ex.getMessage}!")
  }

  manager.shutdown()

}
```
