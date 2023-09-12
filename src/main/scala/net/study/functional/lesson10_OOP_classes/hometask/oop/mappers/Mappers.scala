package net.study.functional.lesson10_OOP_classes.hometask.oop.mappers

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.SignUpRequest

import scala.util.{Failure, Success, Try}

// here you can assign your implicit mapper function  implement this trait with your logic
trait Mappers {

  // implement all
  implicit val signUpRequestMapper: SignUpRequest => SignUpDto = { request =>
    tryMap {
      for {login <- request.login
           name <- request.name orElse Option("John")// may be default
           surname <- request.surname
           hash <- request.pass
           msisdn <- request.msisdn
           } yield SignUpDto(login, name, surname, hash, msisdn)
    }
  }

  def tryMap[OUT](process: => Option[OUT]): OUT = Try(process) match {
    case Failure(ex)  => throw ex
    case Success(opt) => opt getOrElse (throw new RuntimeException("lack of params"))
  }
}