package net.study.functional.lesson_14_futures

///import scala.concurrent.ExecutionContext.Implicits.global

import java.util.concurrent.Executors
import scala.concurrent.duration.{Duration, DurationInt}
import scala.concurrent.{Await, ExecutionContext, ExecutionContextExecutorService, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success}

object FuturesApi extends App {

  println(Thread.currentThread().getName)

  private val executorService = Executors.newSingleThreadExecutor()

  implicit val executionContext: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(executorService)

  val simpleFuture: Future[String] = Future("hello")

  val simpleFuture2: Future[String] = Future {
    /*   println(Thread.currentThread().getName)
       println("some action")*/
    "World"
  }

  val futureFlattened: Future[String] = for {
    r1 <- simpleFuture
    r2 <- simpleFuture2
  } yield r1 + " " + r2

  val flatMapCallOnce: Future[String] = simpleFuture.flatMap(r1 => simpleFuture2.map(_ + r1))

  val flattened: Future[String] = simpleFuture.flatMap(r1 => simpleFuture2.flatMap(r2 => Future.apply(r2 + r1)))

  /// Attention it's blocking
  val result = Await.result(futureFlattened, Duration.Inf)

  println(result)


  /*  val successfulFuture: Future[String] = Future.successful("")

    val failedFuture: Future[Nothing] = Future.failed(new RuntimeException("some exp"))

    val successfulFuture333: Future[String] = failedFuture*/

  // Future api -- callback

  val myTask = Future {
    1 + 1
  }


  val myTaskWithException = Future {
    5 / 0
  }

  val myTaskWithException2 = Future {
    5 / 0
  }

  val callBack: Unit = myTaskWithException onComplete {
    case Success(value)     => println(value)
    case Failure(exception) => println(exception)
  }

  // -- foreach only for success
  val foreachResult: Unit = myTask foreach println

  // Error handling

  // fallBackTo

  val alternativeContext = Future(0)

  val alternativeResult: Future[Int] = myTaskWithException fallbackTo alternativeContext

  println(Await.result(alternativeResult, 3 seconds))

  // recover

  val alternativeContextWithException = Future.failed(new RuntimeException("Oooops something went wrong"))

  def syncMethod: Int = throw new RuntimeException("Oooops something went wrong")

  val recovered: Future[Int] = (myTaskWithException fallbackTo alternativeContextWithException) recover {
    case anyEx: Throwable =>
      println(anyEx)
      100 //syncMethod
  }

  println(Await.result(recovered, 3 seconds))

  // recoveredWith

  def alternativeAsyncApi(error: Throwable) = Future(111)

  val recoveredWithAnotherContext = myTaskWithException recoverWith {
    case e: Throwable =>

      alternativeAsyncApi(e)
  }

  // transformers

  // transform

  val helloWorld = Future("HelloWorld")

  val transformedSuccessfully: Future[String] = helloWorld transform {
    case Success(value)     => Success(value + "!!!!!")
    case Failure(exception) => Failure(new RuntimeException("Failed"))
  }


}
