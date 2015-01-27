package com.vngrs.scala.pushy

import java.io.InputStream
import java.security.KeyStore
import javax.net.ssl.{ SSLContext => JavaSSLContext }

import com.relayrides.pushy.apns.util.SSLContextUtil

import scala.util.Try

object SSLContext {

  def apply(file: String, password: String): Try[JavaSSLContext] =
    Try(SSLContextUtil.createDefaultSSLContext(file, password))

  def apply(inputStream: InputStream, password: String): Try[JavaSSLContext] =
    Try(SSLContextUtil.createDefaultSSLContext(inputStream, password))

  def apply(keyStore: KeyStore, password: Array[Char]): Try[JavaSSLContext] =
    Try(SSLContextUtil.createDefaultSSLContext(keyStore, password))

}
