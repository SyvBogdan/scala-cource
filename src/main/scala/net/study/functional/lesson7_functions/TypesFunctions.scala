package net.study.functional.lesson7_functions

import scala.annotation.tailrec

object TypesFunctions extends App {

  type LibraryComputationType = Int => Int

  //////// partial applied function
  def sum(a: Int, b: Int): Int = a + b

 // 1 // when all parameter not available
  val sumFunc: (Int, Int) => Int = sum _

  // a few nanoseconds later

  // computation done
  val r1: Int = sumFunc(1, 2)

  // 2) partial parameter assignment

  val partialSumApplying: Int => Int = sumFunc(1, _: Int)

  // a few nanoseconds later in onother sub function

  val r2: Int = partialSumApplying(2)

  def adder(d: Int, func: LibraryComputationType): Int = func(d * 2)

  println(adder(5, partialSumApplying))


  ////// currying

  def sumTriple(a: Int, b: Int, c: Int): Int = a + b + c // very hard function

  val sumTripleFunc: (Int, Int, Int) => Int = sumTriple _

  val sumTripleCurrFunc: Int => Int => Int => Int = (sumTriple _).curried

  val another2: Int => Int => Int = sumTripleCurrFunc(0)

  val another3: Int => Int = another2(1)

  val resultOfCurring: Int = another3(5)


  val uncurriedFunc = Function.uncurried(another2)

  //// high order functions

  def biFunction(a: Int, b: Int, computationFunc: (Int, Int) => Int) = {
    // some computation
    println((a, b))
    computationFunc(a, b)
  }

  val partialFunctionTripleFunc: (Int, Int) => Int = sumTriple(10, _: Int, _: Int)

  val sumResult = biFunction(1, 2, _ + _)

  val division = biFunction(1, 2, _ / _)

  val multiplying = biFunction(1, 2, _ * _)

  val anotherResult = biFunction(1, 2, partialFunctionTripleFunc)

  ////  inner method or function

  // outer function/ method
  def factorial(number: Int): Int = {

    // inner function/method
    @tailrec
    def factorialCompute(n: Int, acc: Int = 1): Int = {

      if (n <= 1) acc else factorialCompute(n - 1, acc * n)
    }
    factorialCompute(number)
  }

  println(factorial(100))





}
