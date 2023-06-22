package net.study.functional.lesson5_pattern_matching

import scala.language.postfixOps

object Lesson5 extends App {

  val intSeq: Seq[Int] = 1 to 10

  1 // Simple constant pattern matching

  val maybeInt: AnyVal = true

  maybeInt match {
    case 1     => println(1)
    case 10.0F => println(10)
    case true  => println("bool")
    case _     => println()
  }


  2 // case class unapply

  case class Person(name: String, age: Int)

  val person: Any = Person("Andriy", 20)

  person match {
    case Person(name, age) =>
    case _                 =>
  }

  3 // Tuple matching


  val simpleTuplePerson: (String, Int) = ("Andriy", 22)

  val toPerson = (Person.apply _) tupled

  println(toPerson(simpleTuplePerson))

  val simpleTuple: (Int, String) = (1, "2")
  val (myInt, myString)          = simpleTuple

  simpleTuple match {
    case (myInt, myString) =>
    case _                 =>
  }

}
