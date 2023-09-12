package net.study.functional.lesson10_OOP_classes.hometask.oop.validator

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{AbsentError, EmptyStringError, Error, ListEmptyError, NotLatinError, NotLatinOrDigitError, ValidationError, WrongMsisdnError}

import java.util.UUID
import scala.util.matching.Regex

object ValidatorUtil {

  def validateAbsentParam(paramName: String, maybeEmpty: Option[_]): Either[ValidationError, Unit] =
    maybeEmpty.map(_ => ()).toRight(new ValidationError(paramName, AbsentError))

  def validateStringEmptyParam(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    validateOnlyPresent(paramName, maybeStringEmpty, EmptyStringError)(_.nonEmpty)

  def validateTraversableEmpty[T <: Traversable[_]](paramName: String, paramValue: Option[T], cid: UUID): Either[ValidationError, Unit] =
    validateOnlyPresent(paramName, paramValue, ListEmptyError) (_.nonEmpty)

  def validateTraversableEmptyNodes[T <: Traversable[String]](paramName: String, paramValue: Option[T], cid: UUID): Either[ValidationError, Unit]=
    validateOnlyPresent(paramName, paramValue, ListEmptyError) (_.forall(_.nonEmpty))

  def validateOnlyLatin(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    validateOnlyPresent(paramName, maybeStringEmpty, NotLatinError)(toCheck => "[a-zA-Z]+".r.pattern.matcher(toCheck).matches())

  def validateOnlyLatinAndDigits(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    validateOnlyPresent(paramName, maybeStringEmpty, NotLatinOrDigitError)(toCheck => checkPattern(toCheck, "[a-zA-Z1-9]+".r))

  def validateMsisdn(paramName: String, maybeStringEmpty: Option[String]): Either[ValidationError, Unit] =
    validateOnlyPresent(paramName, maybeStringEmpty, WrongMsisdnError)(toCheck => checkPattern(toCheck, "(^\\d{9}+$)|(^\\d{12}+$)".r))

  def validateOnlyPresent[P](paramName: String, optValue: Option[P], ifError: Error)(condition: P => Boolean): Either[ValidationError, Unit] = optValue match {
    case Some(toCheck) => if (condition(toCheck)) Right(()) else Left(new ValidationError(paramName, ifError))
    case None          => Right(())
  }

  private def checkPattern(toCheck: String , pattern: Regex): Boolean = toCheck match {
    case pattern(_) => true
    case _ => false
  }

}
