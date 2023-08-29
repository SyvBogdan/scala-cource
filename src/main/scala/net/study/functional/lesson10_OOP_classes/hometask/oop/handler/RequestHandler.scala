package net.study.functional.lesson10_OOP_classes.hometask.oop.handler

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.Error
import net.study.functional.lesson10_OOP_classes.hometask.oop.mappers.Mapper
import net.study.functional.lesson10_OOP_classes.hometask.oop.processor.Processor
import net.study.functional.lesson10_OOP_classes.hometask.oop.validator.RequestValidator


// implement this abstraction use self type trait mixin to implement validate -> map -> process logic
// you can't change signature or self type abstraction
trait RequestHandler[R, DTO, RESP] {

  this: RequestValidator[R] with Mapper[R, DTO] with Processor[DTO, RESP] =>

  def handle(request: R)(implicit mapperFunc: R => DTO): Either[Error, RESP] = ???

}


