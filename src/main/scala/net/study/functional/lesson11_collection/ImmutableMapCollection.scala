package net.study.functional.lesson11_collection

object ImmutableMapCollection extends App {

  println(s"========================Map=============================")

  println("=========================Creation=========================")

  val tuple1: (Int, String) = (4, "4")
  val tuple2: (Int, String) = 5 -> "5"

  val fromPattern = Map(1 -> "1", 2 -> "2", 3 -> "3")

  val fromTuples = Map(tuple1, (2, "2"), 3 -> "3")

  val fromList: Map[Int, String] = (1 :: 2 :: 3 :: Nil).zipWithIndex.map(c => c._1 -> c._2.toString) toMap

  val fromTupleList: Map[Int, String] = (4, "4") :: (3, "3") :: Nil toMap

  val fromEmptyMap: Map[Int, String] = Map.empty + ((4, "4")) + ((3, "3"))

  val fromEmptyMap2: Map[Int, String] = Map() + (1 -> "1") + (2 -> "2")

  println("======================Get=========================")

  val simpleSavedGet: Option[String] = fromPattern get 1

  val simpleDangerousGet: String = fromPattern(1) // can be error

  val safeMap = fromPattern.withDefaultValue("default value")

  val safeResult = safeMap(2000000) //"default value"

  val safeMapWithDefaultFunc = fromPattern.withDefault(requested => requested.toString)
  val safeResult2            = safeMapWithDefaultFunc(1000)
  val safeResult2Opt         = safeMapWithDefaultFunc get 1000

  val safeResultOrElse = safeMapWithDefaultFunc getOrElse(200, "200")

  println(s"safeResult: $safeResult")

  println(s"safeResult2: $safeResult2")

  println(s"safeResult2Opt: $safeResult2Opt")

  println("==================PUT======================")

  val witUpdateOrInsertedEntry: Map[Int, String] = fromPattern + (7 -> "7") + (8 -> "8")

  val witUpdateOrInsertedEntry2: Map[Int, String] = fromPattern.updated(7, "7")

  val appenderMap = Map(100 -> "100", 200 -> "202")

  // merge map
  val appended = fromPattern ++ appenderMap

  println("===================Remove=======================")

  val removeMap = fromPattern - 1 - 2

  val removeByPatch = fromPattern -- Seq(1, 2)

  println(s"removeMap: $removeMap")
  println(s"removeByPatch: $removeByPatch")

  println("===================Mapping and filtering=======================")

  val mappedUsually     : Map[Int, Int]    = fromPattern.map(c => (c._1, c._2.toInt))
  val mappedWithValues  : Map[Int, Int]    = fromPattern.mapValues(_.toInt)
  val filteringWithIndex: Map[Int, String] = fromPattern.filterKeys(_ % 2 == 0)
}
