package net.study.functional.lesson10_OOP_classes

import net.study.functional.Analisys

object SelfTypedTraits extends App {


  val programmer = new Child() with ComputerScience with Programmer

  programmer.writeProgram()

}


trait Runner {

  val name: String

  def run() = {println(s"$name is running")}

}

trait ComputerWorker {

  self: Person1 =>

  def workWithComputer(): Unit = {
    println(s"${name} is working")
  }
}

trait Programmer {

  self: Person1 with ComputerScience =>

  def writeProgram() = {
    println(s"${name} is writing program using $programmingLanguage")
  }
}

trait ComputerScience {

  def programmingLanguage = "Scala"
}

trait WithBusinessLogic {

  self: Analisys =>

  def showBusinessLogic = {
    self.createBusinessLogic
  }

}

/// class Animal extends ComputerWorker

class Child extends Person1 /*with Programmer*/ {
  override val name: String = "child"
}

class Person1 extends ComputerWorker with Programmer with ComputerScience {
  val name: String = "adult"
}

class BusinessAnalysis extends WithBusinessLogic {

  def createBusinessLogic: Unit = {println("Write Business logic")}
}


