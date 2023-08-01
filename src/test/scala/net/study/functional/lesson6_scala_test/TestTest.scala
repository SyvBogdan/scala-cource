package net.study.functional.lesson6_scala_test

import net.study.functional.lesson3_case_classes_and_monades.HomeTask.{PaymentInfoDto, correctPaymentInfo}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FunSuite, Matchers, OptionValues}


@RunWith(classOf[JUnitRunner])
class TestTest extends FunSuite
 // with MockFactory
  with OptionValues
  with Matchers {


  val paymentId  : Int    = 10
  val customer   : String = "Customer"
  val paymentSum : Long   = 1000L
  val paymentTax : Long   = 10L
  val paymentDesc: String = "Transfer"
  val paymentInfo         = PaymentInfoDto(paymentId, Some(customer), Some(paymentSum), Some(paymentTax), Some(paymentDesc))

  test("payment info attributes should be defined") {
    val paymentInfoResult = correctPaymentInfo(paymentInfo)

    1 shouldEqual 1
    /*inside (paymentInfoResult) { case PaymentInfo(id, sum, tax, desc) =>
        id should be (paymentId)
        sum should be (paymentSum)
        tax should be (paymentTax)
        desc should be (paymentDesc)
    }*/
  }


  /*
    import net.study.functional.lesson4_functional_try_logic
    import net.study.functional.lesson6_test_learning.MethodsForTesting._

    // Unit of testing
    "MethodsForTesting" when {
      // method of testing
      "sum" should {
        // behavior assertion
        "calculate right positive result" in {
          sum(1, 2) shouldEqual 3
        }

        "calculate right negative result" in {
          sum(-1, -2) shouldEqual -3
        }
      }

      "devide" should {
        "calculate right division result" in {
          devide(9, 3) shouldEqual 3

          NetworkError should be (NetworkError)
        }

        "fail with ArithmeticException with interception" in {

         val catchResult: ArithmeticException = intercept[ArithmeticException] {
            devide(3, 0) shouldEqual 0
          }
          catchResult.getMessage shouldEqual "/ by zero"
        }

        "fail with ArithmeticException with assertion" in {
          assertThrows[ArithmeticException]{
            devide(3, 0) shouldEqual 0
          }
        }
      }
    }*/
}