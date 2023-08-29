package net.study.functional.lesson10_OOP_classes.hometask.oop.processor

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.Error
trait Processor[DTO, RESP] {

  def process(in: DTO): Either[Error, RESP]

}


//  Response status Always wil be ok for just now
trait Status

case object OK extends Status
