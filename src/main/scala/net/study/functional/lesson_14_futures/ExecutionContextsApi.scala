package net.study.functional.lesson_14_futures

import org.slf4j.{Logger, LoggerFactory}

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, Future, blocking}
import scala.language.postfixOps

object ExecutionContextsApi extends App {

  val logger: Logger = LoggerFactory.getLogger(this.getClass)

  implicit val customExecContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(3000, MyThreadFactory("my-worker")))

  def blockingMethod(): Unit = {
    Thread.sleep(100)
  }

  val start = System.currentTimeMillis()

  val calls: Seq[Future[Unit]] = (1 to 50000) map { n =>
    Future {
      logger.info(s"Start operation $n")
      blocking {
        blockingMethod()
      }
      logger.info(s"End operation $n")
    }
  }

  val collectedResult = Future.sequence(calls).onComplete { _ =>
    val end  = System.currentTimeMillis()
    val time = end - start
    logger.info(s"=================== Total Time in ms: $time")
  }

  Thread.sleep(100000)

}
