package net.study.functional.lesson10_OOP_classes.hometask.oop.mappers

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{Error, LackMappedParamError, MapperError}
import net.study.functional.lesson10_OOP_classes.hometask.oop.exception.MapperException
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.SignUpRequest
import net.study.functional.lesson10_OOP_classes.hometask.oop.services.{HashService}

import scala.util.Try

private[oop] trait Mapper[R, DTO] {
  def map(request: R)(implicit defaultMapper: R => DTO): Either[Error, DTO]
}

trait SignUpMapper extends Mapper[SignUpRequest, SignUpDto] {

  val hashService: HashService

  override def map(request: SignUpRequest)(implicit mapper: SignUpRequest => SignUpDto): Either[Error, SignUpDto] =
    Try(mapper(request))
      .toEither
      .right.map(dto => dto.copy(secret = hashService.hash(dto.secret)))
      .left.map {
      case ex: MapperException => LackMappedParamError
      case _                   => MapperError
    }
}

