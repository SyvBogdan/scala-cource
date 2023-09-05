package net.study.functional.lesson_12_mutable_collection

import scala.collection.mutable

object MutableCollections extends App {

  // Arrays

  //Arrays' creation

  val arrayFromSize    = new Array[Int](5)
  val arrayFromFactory = Array(1, 2, 3, 4, 5)

  val list          = List(1, 2, 3, 4, 5)
  val iterator      = list.iterator
  val arrayFromList = list.toArray

  val intArray = Array.fill(5) {
    iterator.next() // generation method
  }

  println(intArray.mkString(","))

  val wrappedArray: mutable.WrappedArray[Int] = intArray.reverse

  val anotherArray2 = intArray.reverse

  println(wrappedArray)

  println(anotherArray2.mkString("Array(", ", ", ")"))

  val arrayFromRange = Array.range(1, 4)

  val arrayFromRangeWithPositiveStep = Array.range(1, 4, 2)

  val arrayFromRangeWithNegativeStep = Array.range(10, 4, -2)

  println(s"arrayFromRange: ${arrayFromRange.mkString("Array(", ", ", ")")}")
  println(s"arrayFromRangeWithPositiveStep: ${arrayFromRangeWithPositiveStep.mkString("Array(", ", ", ")")}")
  println(s"arrayFromRangeWithNegativeStep: ${arrayFromRangeWithNegativeStep.mkString("Array(", ", ", ")")}")

  // update
  arrayFromSize(0) = 1
  arrayFromSize(1) = 2
  arrayFromSize update(2, 3)

  println(arrayFromSize.mkString("Array(", ", ", ")"))

  // copy

  val array = Array(10, 12, 14, 16)

  array.copyToArray(arrayFromSize)

  println(arrayFromSize.mkString("Array(", ", ", ")"))

  // seq api

  val newFilterArray: Array[Int] = array.filter(_ > 2)
  val newMappedArray             = array.map(_.toString)

  // ArraySeq

  val arraySeq: mutable.Seq[Int] = mutable.ArraySeq(1, 2, 3)

  println(s"array: $arraySeq")

  // ArrayBuffer

  val arrayBuffer = mutable.ArrayBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9)

  // append single / all

  arrayBuffer += 10

  println(s"arrayBuffer append 10: $arrayBuffer")

  arrayBuffer ++= Seq(12, 13)

  arrayBuffer.appendAll(Seq(14, 15))

  println(s"arrayBuffer append Seq(12,13): $arrayBuffer")

  arrayBuffer.insert(0, 100)

  println(s"arrayBuffer append insert 100 to 0 index: $arrayBuffer")


  arrayBuffer.insertAll(5, Seq(200, 300, 400))

  println(s"arrayBuffer append insert 100 to 5 index Range: $arrayBuffer")

  //Removing

 // arrayBuffer -= 2

   // by
  arrayBuffer --= Seq(100, 1)

  println(s"arrayBuffer with removing by values: $arrayBuffer")

  arrayBuffer remove 0

  arrayBuffer remove(0, 2)

  println(s"arrayBuffer with removing by index 2: $arrayBuffer")

}
