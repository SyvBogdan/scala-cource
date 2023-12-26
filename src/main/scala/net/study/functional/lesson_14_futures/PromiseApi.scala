package net.study.functional.lesson_14_futures

import org.slf4j.{Logger, LoggerFactory}

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future, Promise}
import scala.language.postfixOps

object PromiseApi extends App {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  trait ResponseListener {

    def onSuccess(cid: UUID): Unit

    def onFailure(throwable: Throwable): Unit
  }

  def sendEvent(event: String, responseListener: ResponseListener): Unit = {
    Future {
      val uuid = UUID.randomUUID()
      logger.info(s"$uuid task initiated")
      Thread.sleep(2000)
      logger.info(s"$uuid task internally done")
      if (event.isEmpty) responseListener.onFailure(new RuntimeException("Some error")) else responseListener.onSuccess(UUID.randomUUID())
    }
  }

  def sendEvent(event: String): Future[UUID] = {
    val promise = Promise[UUID]()

    val listener = new ResponseListener {
      override def onSuccess(cid: UUID): Unit = {
        // TODO any post success callback logic
        logger.info(s"$cid successfully done")
        // promise.complete(Success(cid))
        promise.success(cid)
      }

      override def onFailure(throwable: Throwable): Unit = {
        // TODO any post failure callback logic
        logger.error(s"${UUID.randomUUID()} failure to process event")
        // promise.complete(Failure(throwable))
        promise.failure(throwable)
      }
    }

    sendEvent(event, listener)

    promise.future
  }

  val futureResult: Future[UUID] = sendEvent("").map { r =>
    logger.info(s"$r got result")
    r
  } recover {
    case thr: Throwable =>
      logger.error(s"got error: $thr")
      UUID.randomUUID()
  }

  Await.result(futureResult, 20 seconds)
}
