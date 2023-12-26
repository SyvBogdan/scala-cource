package net.study.functional.lesson_14_futures

import net.study.functional.lesson4_functional_try_logic.Lesson4.NetworkException

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object HomeTask extends App {

  case class Account(id: Int, login: String)


  val db1 = Map(
    1 -> Account(1, "customerA"),
    2 -> Account(2, "customerB"),
    3 -> Account(3, "customerC"),
    )

  val db2 = Map(
    4 -> Account(4, "customerD"),
    5 -> Account(5, "customerE"),
    6 -> Account(6, "customerF"),
    )

  // account DB
  def getAccountInfoDb1(id: Int): Future[Option[Account]] = Future(db1.get(id))

  def getAccountInfoDb2(id: Int): Future[Option[Account]] = Future(db2.get(id))

  case class PaymentInfo(paymentId: Int, name: String, payment: Int)

  val paymentServiceDb = Map(
    "customerA" -> Seq(PaymentInfo(1, "customerA", 1000), PaymentInfo(2, "customerA", 2000)),
    "customerE" -> Seq(PaymentInfo(12, "customerE", 3000), PaymentInfo(15, "customerE", 5001)),
    )

  val paymentServiceDb2 = Map(
    "customerB" -> Seq(PaymentInfo(3, "customerB", 500), PaymentInfo(4, "customerB", 700)),
    "customerD" -> Seq(PaymentInfo(21, "customerE", 3000), PaymentInfo(22, "customerE", 5000)),
    )

  val paymentServiceDb3 = Map(
    "customerC" -> Seq(PaymentInfo(5, "customerC", 4400), PaymentInfo(6, "customerC", 1200), PaymentInfo(7, "customerC", 2200)),
    "customerF" -> Seq(PaymentInfo(33, "customerF", 701), PaymentInfo(58, "customerF", 400))
    )

  // payment sources
  def getPaymentInfo(name: String): Future[Option[Seq[PaymentInfo]]] = Future(paymentServiceDb.get(name))

  def getPaymentInfo2(name: String): Future[Option[Seq[PaymentInfo]]] = Future(paymentServiceDb2.get(name))

  def getPaymentInfo3(name: String): Future[Option[Seq[PaymentInfo]]] = Future(paymentServiceDb3.get(name))


  // check if storno
  def isStorno(paymentId: Int): Future[Boolean] = Future(paymentId % 3 == 0)


  trait Listener {

    def onSuccess(cid: UUID): Unit

    def onFailure(throwable: Throwable): Unit

  }

  def ackPaymentProcessed(paymentInfo: PaymentInfo, listener: Listener): Unit = {

    Future {
      if (paymentInfo.paymentId % 2 == 0) listener.onSuccess(UUID.randomUUID()) else listener.onFailure(NetworkException("connection error"))
    }
  }

  def retryAckPaymentProcessed(paymentInfo: PaymentInfo) = Future(())

  // account to be processed
  val accountIds = Seq(1, 2, 3, 4, 5, 6, 7)

  /*
   1) get account by its ids from available resources(resources can be extended in the future or reduced) ignore if absent
   2) using login get all payments from one of the available payment services(payment services can be extended in the future or reduced)
   3) filter all storno paymnets
   4) make acknowledgement that process was processed by your flow using "ackPaymentProcessed" or sent to retry "retryAckPaymentProcessed"
   5) make aggregation map where where key is "user" and value is all his "payment sum"
   6) all operations must be processed asynchronously!!!!
   7) write test to check your calculations
   */


}
