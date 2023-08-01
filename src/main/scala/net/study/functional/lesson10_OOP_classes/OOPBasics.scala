package net.study.functional.lesson10_OOP_classes

object OOPBasics extends App {

  val person = new Person("John", 18)

  person.name = "Ivan"

  // person.age = 19

}

class Person(var name: String, val age: Int, isMarriage: Boolean) {

  def this(name: String, age: Int) {
    this(name, age, true)
  }

}
