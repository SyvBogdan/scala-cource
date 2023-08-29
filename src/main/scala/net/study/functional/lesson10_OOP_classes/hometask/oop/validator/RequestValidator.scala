package net.study.functional.lesson10_OOP_classes.hometask.oop.validator

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.Error


// here you can implement sub-traits for validation purpose


trait RequestValidator[R] {

  def validate(request: R): Either[Error, R]

}

object RequestValidator {

   val Name    = "name"
   val Surname = "surname"
   val Login   = "login"
   val pass    = "pass"
   val Msisdn  = "msisdn"
}
