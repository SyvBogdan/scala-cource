package net.study.functional.lesson11_collection

import scala.collection.immutable.{LinearSeq, SortedSet}

object ImmutableCollections extends App {


  //// default implementations

  val traversable: Traversable[String] = Traversable("a", "b", "c")

  val iterable: Iterable[String] = Iterable("a", "b", "c")

  val simpleMap: Map[Int, String] = Map(1 -> "1", 2 -> "2") // default HashMap

  val simpleMap2: Map[Int, String] = Map(1 -> "1", 2 -> "2", 3 -> "3", 4 -> "4", 5 -> "5") // default HashMap

  val simpleSet = Set(1, 2, 2, 3) // default HashSet

  val simpleSet2 = Set.apply(1, 2, 2, 3, 5, 6, 8, 9, 0) // default HashSet

  val sortedSet = SortedSet(3, 1, 5)

  val indexedSeq = IndexedSeq(1, 2, 3, 4, 5, 7)

  val linearSeq = LinearSeq(1, 2, 3, 4, 5, 6, 67)

  val seq = Seq(1, 2, 3, 4, 6) // linear

  val range: Range = 1 to 10

  // sequence computations

  seq.filter(x => true)
  seq.filterNot(x => true)
  seq.map(x => x)
  seq.flatMap(x => Seq(x))
  seq.withFilter(x => true) map (x => x) //simultaneously
  seq.sorted
  seq.sortBy(x => x)
  seq.sortWith(/*_.compareTo(_)*/ (left, right) => left.compareTo(right) < 0)
  seq.indices

  seq.tail
  seq.dropWhile(_ > 3)
  seq.takeWhile(_ < 3)
  val dif       = seq diff indexedSeq // leave everything that make difference left collection from right
  val intersect = seq intersect indexedSeq // leave everything that common for both collections

  // aggregator
  // reduce operation

  val aggregator = (acc: Int, next: Int) => acc + next

  seq reduce aggregator // dangerous collection must be not empty

  seq reduceLeft aggregator // dangerous collection must be not empty

  seq reduceRight aggregator // dangerous collection must be not empty

  val acc: Int = 100
  print(s"start left with $acc")
  val leftResult = seq.foldLeft(acc) ((acc, next) => {
    print( " + " + next)
    acc + next
  })
  println(" = " + leftResult)


  val acc2: Int = 100
  print(s"start right 100 ")
  val rightResult = seq.foldRight(acc2)((acc, next) => {
    print(" + " + acc)
    acc + next
  })
  println(" = " + rightResult)

}
