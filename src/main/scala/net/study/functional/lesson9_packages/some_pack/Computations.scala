package net.study.functional.lesson9_packages.some_pack

object Computations {

  implicit def sum(x: Int, y: Int) = x + y

  implicit def multiply(x: Int, y: Int) = x * y

  implicit def divide(x: Int, y: Int) = x / y
}
