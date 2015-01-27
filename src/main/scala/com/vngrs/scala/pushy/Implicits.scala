package com.vngrs.scala.pushy

import scala.language.implicitConversions

object Implicits {

  implicit def toOption[T](value: T): Option[T] = Some(value)

}
