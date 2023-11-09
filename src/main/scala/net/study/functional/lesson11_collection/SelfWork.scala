package net.study.functional.lesson11_collection

object SelfWork extends App {

  /// odd sum event sum
  /// calculate odd sum and even sums from thr current list

  val nums = List(1, 2, 3, 4, 5, 7, 9, 11, 14, 12, 16)

  ////////////////////// write program to calculate the number of occurrences of each member in List

  val list = List(10, 5, 5, 11, 2, 2, 3, 6, 7, 8, 8, 1, 3, 15)


  /////////////////// write program which recognize palindrome in any List


  /////////////////// write function to calculate factorial using tail recursion


  ////////////////////////////////////
  /// Find the most 3 biggest companies
  /// Calculate total capitalization of all companies
  case class Company(name: String, capitalization: Double)

  val companies = Seq(
    Company("Apple", 2720),
    Company("Microsoft", 2570),
    Company("Saudi Arabian Oil", 2140),
    Company("Alphabet (Google)", 1590),
    Company("Amazon", 1490),
    Company("Nvidia", 1050),
    Company("Meta", 0.801),
    Company("Berkshire Hathaway", 0.760),
    Company("Tesla", 0.653),
    Company("Eli Lilly", 0.526)
    )


  /////////////////// calculate turnover per regions excluding partner servers

  val EASTERN  = "EASTERN"
  val WESTERN  = "WESTERN"
  val SOUTHERN = "SOUTHERN"
  val NORTH    = "NORTH"

  val domains = Map(
    "firstDomain.com.ua" -> EASTERN,
    "secondDomain.com.ua" -> WESTERN,
    "thirdDomain.com.ua" -> WESTERN,
    "fourthDomain.com.ua" -> NORTH,
    "fifthDomain.com.ua" -> SOUTHERN,
    "sixthDomain.com.ua" -> EASTERN,
    "seventhDomain.com.ua" -> SOUTHERN,
    "eightDomain.com.ua" -> NORTH,
    )

  val ips = Map(
    "192.168.1.1" -> EASTERN,
    "192.168.1.2" -> EASTERN,
    "192.168.1.3" -> NORTH,
    "192.168.1.4" -> WESTERN,
    "192.168.1.5" -> NORTH,
    "192.168.1.6" -> EASTERN,
    "192.168.1.7" -> WESTERN,
    "192.168.1.8" -> SOUTHERN,
    )

  case class DailyTurnOver(host: String, isPartner: Boolean, turnover: Int)

  val servers = Seq(
    DailyTurnOver("firstDomain.com.ua", true, 10000),
    DailyTurnOver("thirdDomain.com.ua", false, 15000),
    DailyTurnOver("192.168.1.4", false, 15000),
    DailyTurnOver("192.168.1.7", true, 20000),
    DailyTurnOver("fourthDomain.com.ua", false, 13000),
    DailyTurnOver("192.168.1.5", false, 30000),
    DailyTurnOver("eightDomain.com.ua", false, 17000),
    DailyTurnOver("seventhDomain.com.ua", false, 10000),
    DailyTurnOver("sixthDomain.com.ua", false, 10000),
    DailyTurnOver("192.168.1.8", false, 9000),
    )

}
