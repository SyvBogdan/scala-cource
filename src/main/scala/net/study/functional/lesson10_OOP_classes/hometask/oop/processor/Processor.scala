package net.study.functional.lesson10_OOP_classes.hometask.oop.processor

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.Error
import net.study.functional.lesson10_OOP_classes.hometask.oop.response.SignUpResponse

trait Processor[DTO, RESP] {

  def process(in: DTO): Either[Error, RESP]

}

trait SignUpProcessor extends Processor[SignUpDto, SignUpResponse] {
  override def process(in: SignUpDto): Either[Error, SignUpResponse] = Right(SignUpResponse(OK, in.login))
}

//  Response status Always wil be ok for just now
trait Status

case object OK extends Status
