package net.study.functional.lesson10_OOP_classes.hometask.oop

import net.study.functional.lesson10_OOP_classes.hometask.oop.handler.SignUpHandler
import net.study.functional.lesson10_OOP_classes.hometask.oop.mappers.Mappers
import net.study.functional.lesson10_OOP_classes.hometask.oop.request.SignUpRequest
import net.study.functional.lesson10_OOP_classes.hometask.oop.response.SignUpResponse
import net.study.functional.lesson10_OOP_classes.hometask.oop.services.{HashService, LoginService}


import java.util.Date
import scala.language.postfixOps

object HomeTask extends App {

  val signUpRequest = SignUpRequest(name = Option("Bogdan"), surname = Option("Syvulyak"), login = Option("BSyvulyak"), pass = Option("jrfgadkjv.ke"), msisdn = Option("637425766"))

  val route = new SignUpRoute(new LoginService, new HashService)

  route.signUpRoute(signUpRequest)

  class SignUpRoute(override val loginService: LoginService, override val hashService: HashService)
    extends SignUpHandler with Mappers
    {
    // do any other business logic you want
    def signUpRoute(signUpRequest: SignUpRequest): Either[errors.Error, SignUpResponse] = handle(signUpRequest)

  }
}




