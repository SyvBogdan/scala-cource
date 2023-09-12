package net.study.functional.lesson13_generics

trait Animal {
  def name: String
}

class Cat(override val name: String) extends Animal

class SuperCat(override val name: String) extends Cat(name)

class Dog(override val name: String) extends Animal
