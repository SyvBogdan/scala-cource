package net.study.functional.lesson3_case_classes_and_monades

object Lesson3 extends App {

  case class Person(name: String, age: Int = 30)

  val personA = Person.apply("Sasha", 20)

  val personB = Person.apply("Ivan", 21)

  val personC = Person.apply("Serhiy", 21)

  /*  println(personA == personB)

    println(personA equals personB)

    println(personA eq personB)

    println(personA eq personA)*/

  def analyze(person: Person): String = person match {
    case Person(name, 20) => s"name=$name, age=20"
    case Person("Ivan", age) => s"name=Ivan, age=$age"
    case a => s"another person = $a"
  }

  val personD = Person(name = "Oleg")

  /*  println(analyze(personA))
    println(analyze(personB))
    println(analyze(personC))*/

  /// OPTIONS started

  val maybeInt: Option[Int] = Option(1)

  val maybeInt2: Option[Int] = None

  val resultContext = maybeInt match {
    case Some(a) => a
    case None => 0
  }

  (maybeInt orElse Some({
    println("Calculate alternative")
    1
  })) match {
    case Some(value) => println(s"to do smth $value")
    case None => println("to do smth none")
  }

  /* val result = maybeInt2.getOrElse({
     println("Calculate alternative")
     1
   })*/


}


