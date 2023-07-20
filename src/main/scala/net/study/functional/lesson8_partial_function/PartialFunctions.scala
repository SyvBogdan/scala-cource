package net.study.functional.lesson8_partial_function

import scala.annotation.tailrec
import scala.language.postfixOps

object PartialFunctions extends App {

  /*    val division = new PartialFunction[Int, Int] {

          override def isDefinedAt(x: Int): Boolean = x != 0

          override def apply(x: Int): Int = 100 / x
      }

      println(division(0))*/

  /////////////////////////////////EDUCATIONAL BACKGROUND/////////////////////////////////////////

  val divisionNonZero: PartialFunction[Int, Either[Int, Int]] = {
    case x if x != 0 => Right(100 / x)
  }

  val alternative: PartialFunction[Int, Either[Int, Int]] = {
    case _ => Left(0)
  }

  val andThenFunction: PartialFunction[Either[Int, Int], Option[Int]] = {
    case x => x.toOption
  }

  val division: PartialFunction[Int, Option[Int]] = (divisionNonZero orElse alternative) andThen (andThenFunction orElse andThenFunction)

  //println(division(0))

  /////////////////////////////////////ENTERPRISE USAGE(REAL TRASH)///////////////////////////////////////////////////

  type MyDomainType = PartialFunction[Int, Either[Int, Int]]

  def chainDomains(domains: List[MyDomainType]): Option[MyDomainType] = {
    @tailrec
    def chain(domains: List[MyDomainType], acc: MyDomainType): MyDomainType = domains match {
      case Nil          => acc
      case head :: tail => chain(tail, acc orElse head)
    }

    domains.headOption map (firstDomain => chain(domains.tail, firstDomain))
  }

  val domainList = List()

  val chainedDomainFunction: MyDomainType = chainDomains(domainList) getOrElse alternative

  println(chainedDomainFunction(10))

  //lifting
  //////////////////

  val liftedFunction = ((divisionNonZero orElse alternative) lift)

  val seqInt = Seq(1, 2)

  val param3: Option[Int] = seqInt.lift(3)

  /////////////////Function1////////////////////////

  val square = (a: Int) => a * a

  val adder = (b: Int) => b + 2

  val composed = square compose adder

  val chained = square andThen adder

  //println(composed(10))
  //println(chained(10))


}
