package net.study.functional.lesson4_functional_try_logic

import net.study.functional.lesson4_functional_try_logic.Lesson4.{NetworkException, usingSource, usingSourceWithFinally}
import net.study.functional.usingSourceWithFinally

import scala.io.BufferedSource
import scala.io.Source._
import scala.language.postfixOps
import scala.util.Try

object HomeTask extends App {

  // 1) try to read file from external system over network (use method getFile with two columns: 1) msisdn, subscriber type)
  // and don't forget to close resource after usage!!!!!!

  // 2) try to enrich get Data using main source getDataFromMainSource

  // 3) if fail to execute step 2) go to alternative source and try once more ( use getDataFromAlternativeSource)

  // 4) if success to do so, try to send to 3-d party system all list

  // 5) Implement enrichAndSend method with proper Left(Error) type or Rigiht[Int] Quantity of msisdns send to our third party system

  // Conditions:
  // use only Try Monad to resolve all problems with exception handling
  // You can use any additional custom functions / methods
  // Don't use method Try monad methods as get, getOrElse, isSuccess, isFailure !!!!!

  /// ===============help code ======================

  trait Error

  case object NetworkError extends Error // if sftp server not available

  case object SourceTemporaryUnavailableError extends Error // if main source main source unavailable

  case object AllSourceTemporaryUnavailableError extends Error //if all source were unavailable

  case object ThirdPartySystemError extends Error //if 3-d party system error

  case class TemporaryUnavailableException(string: String) extends Exception

  case class ThirdPartySystemException(string: String) extends Exception

  case class SubscriberInfo(msisdn: String, subscriberType: Int, isActive: Boolean)

  val fileSource = "src/main/resources/lesson4/externalSourceFile.txt"

  // do not change this methods !!!!
  @throws[NetworkException]
  def getFile(isRisky: Boolean, source: String) = if (isRisky) throw NetworkException("SFTP server network exception") else fromFile(source)

  @throws[TemporaryUnavailableException]
  def getActiveData(isRisky: Boolean, msisdns: Seq[String]) = if (isRisky) throw TemporaryUnavailableException("Temporary Unavailable Exception") else {
    msisdns.map(m => (m, if (m.toInt % 2 == 0) 1 else 0))
  }

  @throws[TemporaryUnavailableException]
  def getDataFromMainSource(isRisky: Boolean, msisdns: Seq[String]) = getActiveData(isRisky, msisdns)

  @throws[TemporaryUnavailableException]
  def getDataFromAlternativeSource(isRisky: Boolean, msisdns: Seq[String]) = getActiveData(isRisky, msisdns)

  def sendToProvider(isRisky: Boolean, subscribers: Seq[SubscriberInfo]): Unit =
    if (isRisky) throw ThirdPartySystemException("third party system exception") else {
      subscribers.foreach(m => println(s"Sent $m"))
    }

  /// LEVEL 1 or 1-st scope, the most narrow scope where you will support main business logic on your unit(subscriber) processing / part of subtransaction

  // add error for any unhandled error logic
  case object AnyOtherError extends Error // to handle anything

  // any encapsulated business validation and logic you want!!!!!!!!!!!!! (SingleResponsibility Subscriber parsing with default active state false)
  val toSubscriber = (line: String) => Option(line.split(";")).filter(_.length == 2) map {
    case Array(x, y) => SubscriberInfo(x.trim, y.trim.toInt, isActive = false)
  }  orElse Some( throw new RuntimeException(s"parse error $line")) //if you want catch this, but i'll ignore it

  // any mapping you want with your business logic (SingleResponsibility chaining with mapping our / and filtering) and close your resource if needed
  val toSubscribers = (source: BufferedSource) => usingSource(source)(buffer => (buffer.getLines() map toSubscriber toList) flatten)

  // any enrichment logic you want !!!!!!
  val enrichSubscriber = (subscriberInfo: SubscriberInfo, searchResult: Option[Int]) =>
    searchResult map (activeState => subscriberInfo.copy(isActive = activeState == 1)) orElse Some(subscriberInfo)

  // LEVEL2 or 2-d scope where you spawn your try Monad here with first level of your exception encapsulation. / subtransactions

  def tryGetSubscribers(isRisky: Boolean, source: String): Try[Seq[SubscriberInfo]] = {
    println("tryGetSubscribers")
    Try(getFile(isRisky, source)) map (b => usingSourceWithFinally(b)(source => toSubscribers(source))(println("execution finally")))
  }

  // encapsulate logic for retrieving your enrichment
  def tryGetEnrichmentSource(isRiskyMain: Boolean, isRiskySecond: Boolean, subscribers: Seq[String]): Try[Map[String, Int]] = {
    println("tryGetEnrichmentSource")
    (Try(getDataFromMainSource(isRiskyMain, subscribers)) orElse Try(getDataFromAlternativeSource(isRiskySecond, subscribers))) map (_.toMap)
  }

  def tryToEnrichWithSource(subscribers: Seq[SubscriberInfo], enrichmentSource: Map[String, Int]): Try[Seq[SubscriberInfo]] = Try {
    println("tryToEnrichWithSource")
    for {poorSubscriber <- subscribers
         richSubscriber <- enrichSubscriber(poorSubscriber, enrichmentSource.get(poorSubscriber.msisdn))
         } yield richSubscriber
  }

  def tryToSendToProvider(isRisky: Boolean, subscribers: Seq[SubscriberInfo]): Try[Unit] = Try(sendToProvider(isRisky, subscribers))

  // LEVEL 3 or 3-d scope, your main atomic transaction which consists ONLY from your computation Monad context and finalize it with any transformation you want

  def enrichAndSend(
                     getFileIsRisky: Boolean,
                     getDataFromMainSourceIsRisky: Boolean,
                     getDataFromAlternativeSourceIsRisky: Boolean,
                     sendToProviderIsRisky: Boolean,
                     fileSource: String
                   ): Either[Error, Int] = ((for {
    poorSubscribers <- tryGetSubscribers(getFileIsRisky, fileSource)
    enrichmentSource <- tryGetEnrichmentSource(getDataFromMainSourceIsRisky, getDataFromAlternativeSourceIsRisky, poorSubscribers map (_.msisdn))
    richSubscribers <- tryToEnrichWithSource(poorSubscribers, enrichmentSource)
    _ <- tryToSendToProvider(sendToProviderIsRisky, richSubscribers)
  } yield 1) toEither).left.map {
    case _: NetworkException              => NetworkError
    case _: TemporaryUnavailableException => AllSourceTemporaryUnavailableError
    case _: ThirdPartySystemException     => ThirdPartySystemError
    case unpredictable: Throwable         => println(unpredictable)
      AnyOtherError
  }

  println(enrichAndSend(false, false, true, false, fileSource))


}



