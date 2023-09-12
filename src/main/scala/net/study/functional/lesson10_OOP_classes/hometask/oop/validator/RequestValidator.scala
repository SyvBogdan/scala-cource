package net.study.functional.lesson10_OOP_classes.hometask.oop.validator

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{DuplicateLoginError, Error, ValidationError}
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.SignUpRequest
import net.study.functional.lesson10_OOP_classes.hometask.oop.services.LoginService

trait RequestValidator[R] {

  def validate(request: R): Either[Error, R]

    protected[validator] def withValidations(validations: Either[ValidationError, Unit]*)
                                            (suppress: List[ValidationError] => Either[Error, R]): Either[Error, R] =
      suppress(validations.toList flatMap (_.swap.toOption))

}

object RequestValidator {

  val Name    = "name"
  val Surname = "surname"
  val Login   = "login"
  val pass    = "pass"
  val Msisdn  = "msisdn"

//  private[validator] def withValidations[R](validations: Either[ValidationError, Unit]*)
//                                           (suppress: List[ValidationError] => Either[Error, R]): Either[Error, R] =
//    suppress(validations.toList flatMap (_.swap.toOption))

}

trait SignUpValidator extends RequestValidator[SignUpRequest] {

  import RequestValidator._
  import ValidatorUtil._

  def loginService: LoginService

  override def validate(request: SignUpRequest): Either[Error, SignUpRequest] =
    withValidations(validateStringEmptyParam(Name, request.name),
                    validateOnlyLatin(Name, request.name),
                    validateStringEmptyParam(Surname, request.surname),
                    validateOnlyLatin(Surname, request.surname),
                    validateStringEmptyParam(Login, request.login),
                    validateOnlyLatinAndDigits(Login, request.login),
                    validateUniqueLogin(request.login),
                    validateMsisdn(Msisdn, request.msisdn)) {
      case Nil        => Right(request)
      case head :: tl => Left(tl.foldLeft(head)(_ + _))
    }

  private def validateUniqueLogin(login: Option[String]): Either[ValidationError, Unit] =
    validateOnlyPresent(Login, login, DuplicateLoginError)(loginService.checkUniqueness)
}
