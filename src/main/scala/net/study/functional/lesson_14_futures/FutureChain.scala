package net.study.functional.lesson_14_futures

import net.study.functional.lesson_14_futures.Chain.{FutureAlternative, OrChain}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.Future.successful
import scala.concurrent.duration.DurationInt

object FutureChain extends App {


  val saveToRedis: String => Future[Boolean] = (in: String) => {
    println("try saveToRedis")
    Future(false)
  }

  val saveToKafka: String => Future[Boolean] = (in: String) => {
    println("try saveToKafka")
    Future(false)
  }

  val saveToFile: String => Future[Boolean] = (in: String) => {
    println("try saveToFile")
    Future(false)
  }

  val saveToNfs: String => Future[Boolean] = (in: String) => {
    println("try saveToNfs")
    Future(false)
  }

  val default: String => Future[Boolean] = (in: String) => Future(false)

  def perform(in: String, orNext: String => Future[Boolean]) =
    saveToKafka(in) flatMap (isDone => if (isDone) successful(true) else orNext(in))

  val kafkaChain = new StandardChain[String] {
    override def apply(in: String): Future[Boolean] = saveToKafka(in)
  }

  val redisChain = new StandardChain[String] {
    override def apply(in: String): Future[Boolean] = saveToRedis(in)
  }

  val saveNFS4Chain = new StandardChain[String] {
    override def apply(in: String): Future[Boolean] = saveToNfs(in)
  }

  val saveToFileChain = new StandardChain[String] {
    override def apply(in: String): Future[Boolean] = saveToFile(in)
  }

  val chain: Chain[String] = redisChain orElse kafkaChain orElse saveNFS4Chain orElse saveToFileChain

  val r: Future[Boolean] = chain("")

  val result = Await.result(chain(""), 1 seconds)


  println(result)

}


trait Chain[-IN] extends FutureAlternative[IN] {

  protected def perform(in: IN): Future[Boolean]

  def orElse[A <: IN](nextChain: Chain[A]): Chain[A] = new OrChain[A](this, nextChain)

}

trait StandardChain[IN] extends Chain[IN] {
  override def perform(in: IN): Future[Boolean] = apply(in)
}

object Chain {

  type FutureAlternative[-IN] = IN => Future[Boolean]

  private final class OrChain[IN](left: Chain[IN], right: Chain[IN]) extends Chain[IN] {
    override def perform(in: IN): Future[Boolean] =
      left.perform(in) flatMap (isDone => {
        if (isDone) successful(isDone) else right.perform(in)
      })

    override def apply(v1: IN): Future[Boolean] = (left orElse right) perform v1
  }


}
