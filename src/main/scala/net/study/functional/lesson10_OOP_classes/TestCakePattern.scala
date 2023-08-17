package net.study.functional.lesson10_OOP_classes

object TestCakePattern extends App
 with DbRepository
 with Service {

  override val client = "Oracle client"

  override val anotherService = new AnotherService


}

trait DbRepository {

  val client: String;

  def storeData() = println("Write data")
}


trait Service {

  self: DbRepository =>

  val anotherService: AnotherService

  anotherService.anotherMethod()

  def store() = {

    storeData()

  }

  class AnotherService {

    def anotherMethod() = ""
  }
}


