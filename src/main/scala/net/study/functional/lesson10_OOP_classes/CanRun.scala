package net.study.functional.lesson10_OOP_classes

trait CanRun {

  def speed: Int

  def run = s"run with: $speed"

}

trait CanEat {

  def food: String;

  def eat =  s"eat with: $food"

}

class Bug extends CanRun with CanEat {
  override val speed: Int = 1
  override val food: String = "grass"
}

class Mammal extends CanRun with CanEat {
  override val speed: Int = 60
  override val food: String = "meat"
}
