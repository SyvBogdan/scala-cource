package net.study.functional.lesson4_functional_try_logic

import net.study.functional.RichEither

import scala.language.postfixOps
import scala.util.control.Exception.{Catch, allCatch, catching}
import scala.util.{Failure, Success, Try}

object Lesson4 extends App {

  case class PriorityException(innerMessage: String) extends Exception

  case class MyCustomException(x: String) extends Exception

  case class MyAnotherCustomException(x: String) extends Exception

  case class NetworkException(x: String) extends Exception

  // @throws[RuntimeException]
  def riskyGetMethod(isRisky: Boolean): String = {
    println("processing riskyGetMethod")
    if (isRisky) throw MyCustomException("MyCustomException")
    "Answer1"
  }

  //  @throws[RuntimeException]
  def anotherRiskyGetMethod(isRisky: Boolean): String = {
    println("processing anotherRiskyGetMethod")
    if (isRisky) throw MyAnotherCustomException("MyAnotherCustomException")
    "Answer2"
  }

  // @throws[RuntimeException]
  def veryRiskyGetMethod(isRisky: Boolean): String = {
    println("processing veryRiskyGetMethod")
    if (isRisky) throw NetworkException("networkError")
    "Answer3"
  }

  /// try/catch/finally

  val tryResult: String = try {
    riskyGetMethod(false)
  } catch {
    case PriorityException(innerMessage)                         => innerMessage
    case ex@(_: MyCustomException | _: MyAnotherCustomException) => ex.toString
    case a: Throwable                                            => a.toString
  } finally {
    println("dfgdafgfdfgdafg")
    "FinallyAnswer"
  }

  /// Try Monad

  val x: Try[(String, String, String)] = for {
    r1 <- Try(riskyGetMethod(true))
    r2 <- Try(anotherRiskyGetMethod(false))
    r3 <- Try(veryRiskyGetMethod(false))
  } yield (r1, r2, r3)

  x match {
    case Success(calculated) => //println(calculated)
    case Failure(ex)         => //println(ex)
  }

  // Catch Object

  val commonCatcher: Catch[Nothing] = allCatch

  val specialCatcher: Catch[Nothing] = catching(classOf[MyAnotherCustomException])

  val result      : Try[String]               = allCatch withTry riskyGetMethod(true)
  val resultOpt   : Option[String]            = allCatch opt riskyGetMethod(true)
  val resultEither: Either[Throwable, String] = allCatch either riskyGetMethod(true)

  /*  println(result)
    println(resultOpt)
    println(resultEither.left.map(ex => "MyError"))*/

  //val specialCatcherResult: Try[String] = specialCatcher withTry riskyGetMethod(true)


  val eitherRight: Either[String, Int] = Right(1)

  val eitherLeft: Either[String, Long] = Left("some string")

  val anotherRight: Either[String, Int] = Right(4)

  val pr = eitherLeft.left.map(_ => 0)

  val eitherComputeResult = (for {
    r1 <- eitherRight
    r2 <- eitherLeft orElse Right(8L)
    r3 <- anotherRight
  } yield r1 + r2 + r3) match {
    case Left(x)         => x
    case Right(intValue) => intValue
  }


  println(">>>>>> " + eitherComputeResult)


}


