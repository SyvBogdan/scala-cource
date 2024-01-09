package net.study.functional.lesson_14_futures

import net.study.functional.lesson4_functional_try_logic.Lesson4.NetworkException

import java.util.UUID
import scala.collection.immutable.SortedMap
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Success}

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
  def getAccountInfoDb1(id: Int): Future[Option[Account]] = Future {
    println("call getAccountInfoDb1")
    db1.get(id)
  }

  def getAccountInfoDb2(id: Int): Future[Option[Account]] = Future {
    println("call getAccountInfoDb2")
    db2.get(id)
  }

  case class PaymentInfo(paymentId: Int, name: String, payment: Int)

  val paymentServiceDb = Map(
    "customerA" -> Seq(PaymentInfo(1, "customerA", 1000), PaymentInfo(2, "customerA", 2000)),
    "customerE" -> Seq(PaymentInfo(12, "customerE", 3000), PaymentInfo(17, "customerE", 5001)),
    )

  val paymentServiceDb2 = Map(
    "customerB" -> Seq(PaymentInfo(3, "customerB", 500), PaymentInfo(4, "customerB", 700)),
    "customerD" -> Seq(PaymentInfo(21, "customerD", 3000), PaymentInfo(22, "customerD", 5000)),
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
      if (paymentInfo.paymentId % 2 == 0) {
        println("asc success")
        listener.onSuccess(UUID.randomUUID())
      } else {
        println("asc fail")
        listener.onFailure(NetworkException("connection error"))
      }
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
  ////////////////////////////////////////////////////////////////////////////////////////////////////


  def ascPayment(paymentInfo: PaymentInfo) = {
    val promise = Promise[UUID]()

    val callback = new Listener {

      override def onSuccess(cid: UUID): Unit = promise.success(cid)

      override def onFailure(throwable: Throwable): Unit = promise.failure(throwable)
    }
    ackPaymentProcessed(paymentInfo, callback)
    promise.future
  }

  def ascOrRetryAscPayment(paymentInfo: Option[PaymentInfo]): Future[Option[PaymentInfo]] = paymentInfo match {
    case Some(p) => ascPayment(p) transformWith {
      case Success(_)  => Future.successful(Some(p))
      case Failure(ex) => retryAckPaymentProcessed(p).map(_ => Some(p))
    }
    case None    => Future(None)
  }

  private type FutureRoute[IN, OUT] = IN => Future[Option[OUT]]

  private class NotFound[IN, OUT] extends FutureRoute[IN, OUT] {
    override def apply(v1: IN): Future[Option[OUT]] = Future(None)
  }

  private def chain[IN, OUT](operations: List[FutureRoute[IN, OUT]]): FutureRoute[IN, OUT] = operations match {
    case Nil          => new NotFound
    case ::(head, tl) => new FutureRoute[IN, OUT] {
      override def apply(v1: IN): Future[Option[OUT]] = head(v1) flatMap {
        case Some(out) => Future.successful(Some(out))
        case None      => chain(tl)(v1)
      }
    }
  }

  private def chainAlternative[IN, OUT](in: IN, operations: List[FutureRoute[IN, OUT]]): Future[Option[OUT]] = {

    def inChain(in: IN, operations: List[FutureRoute[IN, OUT]], acc: Future[Option[OUT]]): Future[Option[OUT]] = operations match {
      case Nil          => acc
      case ::(head, tl) => acc.flatMap {
        case Some(out) => Future.successful(Some(out))
        case None      => inChain(in, tl, head(in))
      }
    }

    operations match {
      case Nil          => Future(None)
      case ::(head, tl) => inChain(in, tl, head(in))
    }
  }

  private val accFound: FutureRoute[Int, Account] = chain(List(getAccountInfoDb1, getAccountInfoDb2))

  private val paymentsFound: FutureRoute[String, Seq[PaymentInfo]] = chain(List(getPaymentInfo, getPaymentInfo2, getPaymentInfo3))

  private def findAccount(id: Int) = accFound(id)

  private def findPayments(c: Option[String]) = c match {
    case Some(value) => paymentsFound(value)
    case None        => Future(None)
  }

  private def checkStorno(paymentInfo: PaymentInfo) = isStorno(paymentInfo.paymentId).map(r => if (!r) Some(paymentInfo) else None)

  private def processPayment(payment: PaymentInfo) =
    for {processed <- checkStorno(payment)
         _ <- ascOrRetryAscPayment(processed)}
    yield processed

  private def processPayments(acc: Option[Account], payments: Option[Seq[PaymentInfo]]): Future[Option[(Account, Seq[PaymentInfo])]] =
    payments flatMap (p => acc.map((_, p))) match {
      case Some((acc, payments)) =>
        val futureResults = payments map processPayment
        Future.sequence(futureResults).map(x => Some((acc, x.flatten)))
      case None                  => Future(None)
    }

  private def processAccountPayments(id: Int) =
    for {acc <- findAccount(id)
         pays <- findPayments(acc.map(_.login))
         processed <- processPayments(acc, pays)
         } yield processed


  private def processAccountsPayments(accounts: Seq[Int]): Future[Map[String, Int]] =
    Future.traverse(accounts)(acc => processAccountPayments(acc) map (x => {
      x.map(r => (r._1.login, r._2.map(_.payment).sum))
    })).map(pays => SortedMap[String, Int]() ++ pays.flatten)

  val r = Await.result(processAccountsPayments(accountIds), 3 seconds)

  r.foreach(println)


}
