package net.study.functional.lesson10_OOP_classes.hometask.oop.mappers

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.SignUpRequest

// here you can assign your implicit mapper function  implement this trait with your logic
trait Mappers {

  // implement this
  implicit val signUpRequestMapper: SignUpRequest => SignUpDto = ???


}