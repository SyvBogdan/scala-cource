package net.study.functional.lesson1_basics

import java.util.UUID

object Lesson1 {

  def main(args: Array[String]): Unit = {

    val myString = "name" + 1

    var myChangeableValue = "first value"

    myChangeableValue = "second value"

    def sum(int: Int, int2: Int): Int = int + int2

    val sumFuncFromMethod: (Int, Int) => Int = sum

    val sumFunc: (Int, Int) => Int = (int: Int, int2: Int) => int + int2

    /*    println(sum(Int.MaxValue, Int.MaxValue))
        println(sum(Int.MaxValue, Int.MaxValue))

        println(sumFunc(Int.MaxValue, Int.MaxValue))
        println(sumFunc(Int.MaxValue, Int.MaxValue))*/

    val generateUUIDFunc: () => UUID = {
      val random = UUID.randomUUID()
      () => random
    }

        println(generateUUIDFunc())
        println(generateUUIDFunc())

    def generateUUID: () => UUID = {
      val random = UUID.randomUUID()
      () => random
    }

        println(generateUUID())
        println(generateUUID())


    lazy val myLazyString: String = {
      println("I am ready")
      "Hello!"
    }

    val  postProcessResult: Int => Unit = r => println(r)


    def calculateAndSend(int: Int, int2: Int)(postFunc: Int => Unit): Unit = {
      postFunc(int2 + int)

    }

    val myString1 = ""

    val myInt : Option[Int] = None



  }
}
