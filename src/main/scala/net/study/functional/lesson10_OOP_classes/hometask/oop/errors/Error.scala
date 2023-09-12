package net.study.functional.lesson10_OOP_classes.hometask.oop.errors

sealed trait Error {
  def errorMessage: String
}


// mapper errors
case object LackMappedParamError extends Error {
  override val errorMessage: String = "lackMapperParam"
}

case object MapperError extends Error {
  override val errorMessage: String = "mapperError"
}

// validation Errors
case object EmptyStringError extends Error {
  override val errorMessage: String = "emptyError"
}

case object AbsentError extends Error {
  override def errorMessage: String = "absentParamError"
}

case object ListEmptyError extends Error {
  override def errorMessage: String = "listEmptyError"
}

case object ListEmptyNodesError extends Error {
  override def errorMessage: String = "listEmptyNodesError"
}

case object NotLatinError extends Error {
  override def errorMessage: String = "notLatinError"
}

case object NotLatinOrDigitError extends Error {
  override def errorMessage: String = "notLatinOrDigitError"
}

case object WrongMsisdnError extends Error {
  override def errorMessage: String = "wrongMsisdnError"
}

case object DuplicateLoginError extends Error {
  override def errorMessage: String = "duplicateLoginError"
}

trait CombinedError[T <: CombinedError[T]] extends Error {

  def errors: Map[String, Error]

  def +(error: T): T

}

case class ValidationError(errors: Map[String, Error]) extends CombinedError[ValidationError] {
  require(errors.nonEmpty)

  private val spliterator = ", "

  def this(param: String, error: Error) = this(Map(param -> error))

  // TODO
  // implement this one
  override def +(error: ValidationError): ValidationError = this.copy(errors = this.errors ++ error.errors)

  // TODO
  // implement this  errorMessage using next pattern: errorMessage : [ field1,field2,....], errorMessage2: [field3,field4]
  override val errorMessage: String = ((errors groupBy (_._2.errorMessage)) map { node =>
    val (message, meta) = node
    s"$message : [${meta.keys mkString spliterator}]"
  }) mkString spliterator

  //override def +(error: CombinedError[ValidationError]): ValidationError = ???
}