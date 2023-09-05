package net.study.functional.lesson11_collection

import scala.language.postfixOps
import scala.util.Try

object ImmutableListCollection extends App {

  println("======LIST OVERVIEW=======")


  println("======Creation=======")
  val empty = Nil

  val simpleList: List[Int] = (1 :: (2 :: Nil))

  val simpleListEquality: List[Int] = Nil.::(1).::(2)

  val usualList = List(1, 2, 3, 4)

  println("======Append=======")

  val digitList  = List(1, 2, 3)
  val digitList2 = List(4, 5, 6)
  val toAppend   = 4

  val stringList  = List("a", "b", "c")
  val toAppendStr = "d"


  val appendedList          = toAppend :: digitList
  val appendedList2         = toAppend +: digitList
  val appendWrong: Seq[Any] = stringList +: toAppendStr // don't do this

  val appendAll  = digitList ::: digitList2
  val appendAll2 = digitList ++: digitList2

  println(s"appendedList: $appendedList")

  println(s"appendedListAll: $appendAll")
  println(s"appendedListAll2: $appendAll2")

  println("======Prepend=======")

  val toPrepend = 1000

  val prependedList = digitList :+ toPrepend

  println(s"prependedList: $prependedList")

  println("======Tail=======")

  val someList = 1 :: Nil

  val tailList = someList.tail // dangerous

  val tailResult = Try(tailList.tail) /// wil be error

  println(s"tailList : $tailList")
  println(s"tailResult : $tailResult")

  tailList match {
    case Nil                             => println("list was empty")
    case head :: tail /*::(head, tail)*/ => println(s"list was not empty with head: $head and tail: $tail")
  }

  println("===============remove operation==================")
  val indexToRemove = 2
  val toRemoveList  = List(3, 2, 1, 6, 7)

  // with withFilter
  val removedList = toRemoveList.zipWithIndex.withFilter(t => t._2 != indexToRemove).map(_._1)

  println(s"removeList: $removedList")

  // with splitAt
  val splitted = {

    val (left, right) = toRemoveList splitAt indexToRemove

    left ::: right.tail
  }

  println(s"splitted : $splitted")

  val removedByPatch = toRemoveList.patch(indexToRemove, Nil, 1)

  println(s"removedByPatch : $removedByPatch")

  println("================HEAD/ GET====================")

  val emptyList: List[String] = Nil
  val listWithHead            = List("a")

  val dangerousHead       = Try(emptyList.head) // dangerous operation as emptyLst empty
  val headOption          = emptyList.headOption // None
  val headOptionWithValue = listWithHead.headOption

  assert(headOptionWithValue.contains("a"))

  println(headOption)
  println(headOptionWithValue)

  ///get
  val dangerousGet: Try[String]    = Try(listWithHead(3)) //error
  val safeGet     : Option[String] = listWithHead.lift(30)


}
