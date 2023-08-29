package net.study.functional.lesson10_OOP_classes.hometask.oop.mappers

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.Error

private[oop] trait Mapper[R, DTO] {
  def map(request: R)(implicit defaultMapper: R => DTO): Either[Error, DTO]
}


