package net.study.functional.lesson10_OOP_classes

object DiamondProblem extends App {

  val finalTrait1 = new FinalTrait1 {}

  val finalTrait2 = new FinalTrait2 {}

  println(finalTrait1.test)

  println(finalTrait2.test)

}

trait BasicTrait{
  def test = "test from BasicTrait"
}

trait SecondaryTrait extends BasicTrait {
  override def test: String = "test from SecondaryTrait"
}

trait AnotherSecondaryTrait extends BasicTrait {
  override def test: String = "test from AnotherSecondaryTrait"
}

trait FinalTrait1 extends SecondaryTrait with AnotherSecondaryTrait

trait FinalTrait2 extends AnotherSecondaryTrait with SecondaryTrait