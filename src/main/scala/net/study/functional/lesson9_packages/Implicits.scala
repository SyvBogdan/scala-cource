package net.study.functional.lesson9_packages

import java.util.Date

object Implicits extends App {

  /*  implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())

    def writeWord(personName: String)(implicit word: String, date: Date) = println(personName + " " + word)

    writeWord("John")("f", new Date())*/

  //implicit val someWord = "Hello!"

  def greetings(name: String)(implicit greeting: String): Unit = println(s"$name $greeting")

  def greetings(names: List[String])(implicit greeting: String): Unit = names.foreach { name =>

    greetings(name)
  }

  def greetPeople(names: List[String]) = {

    // import net.study.functional.myGlobalString

    implicit val someWord2 = "Hello!"

    greetings(names)
  }

  val people = List("John", "Jack")

  // greetPeople(people)

  ///// implicit methods/functions////////////

  def computation(x: Int, y: Int)(implicit biFunc: (Int, Int) => Int) = biFunc(x, y)

  {
    import net.study.functional.lesson9_packages.some_pack.Computations.sum
    println(computation(2, 3))
  }

  {
    import net.study.functional.lesson9_packages.some_pack.Computations.multiply
    println(computation(2, 3))
  }

}


