package net.study.functional.lesson10_OOP_classes

object TestSealedTrait extends App {

 // case object MyCustom extends ResponseStatus

  val status = ActiveStatus

  def process(status: ResponseStatus): String = status match {
    case ActiveStatus  => ""
    case StoppedStatus => ""
    case PendingStatus => ""
  }

  process(status)
}
