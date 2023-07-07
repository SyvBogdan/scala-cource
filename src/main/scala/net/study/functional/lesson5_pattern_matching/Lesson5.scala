package net.study.functional.lesson5_pattern_matching

import scala.language.postfixOps

object Lesson5 extends App {

  val intSeq: Seq[Int] = (1 to 10) toList

  1 // Simple constant pattern matching

  val maybeInt: AnyVal = true

  maybeInt match {
    case 1     => println(1)
    case 10.0F => println(10)
    case true  => //println("bool")
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

  val simpleTuplePerson = ("Andriy", 22)

  val toPerson = (Person.apply _) tupled

  println(toPerson(simpleTuplePerson))

  val simpleTuple: (Int, String) = (1, "2")
  val (myInt, myString)          = simpleTuple

  val anotherTuple: (Int, String) = 1 -> "2"

  simpleTuple match {
    case (myInt, myString) =>
    case _                 =>
  }


  4 // seq pattern matching

  val stringSeqEmpty: List[String] = Nil

  val stringSeq: List[String] = "first" :: Nil

  val stringSeq2 = "second" :: stringSeq

  val stringSeq3: List[String] = "second" :: "first" :: Nil

  stringSeq match {
    //case List(a, _*) =>  println(s"head: $a")
    case head :: second :: tail => //println(s"head: $head, second: $second, tail: $tail")
    case head :: tail           => //println(s"head: $head, tail: $tail")
    case Nil                    =>
  }


  // Regex Pattern matching

  val regexpsDigit = """([0-9]+)""".r
  val alphabetical = """([A-Za-z]+)""".r

  val whatToMatch = "12"

  val stringable = "abcZ"

  stringable match {
    case regexpsDigit(someDigits) => //println(someDigits)
    case alphabetical(someChars)  => //println(someChars)
    case another                  => //println("something else")
  }


  // variable binding pattern matching

  person match {
    case person@Person(name, age) => //println(s"Person: $person with name: $name, $age years old")
    case _                        =>
  }


  // type pattern mathing

  person match {
    case person: Person => //println(s"Person: $person")
    case _              =>
  }

  // guard
  person match {
    case person@Person(_, age) if age > 18 => println(s"Person: $person")
    //case veryYoungPerson                   => println(s"very young person: $veryYoungPerson")
  }

}
