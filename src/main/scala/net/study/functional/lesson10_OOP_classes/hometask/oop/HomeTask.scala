package net.study.functional.lesson10_OOP_classes.hometask.oop

import net.study.functional.lesson10_OOP_classes.hometask.oop.errors.{Error, ValidationError}

import java.util.Date
import scala.language.postfixOps

object HomeTask extends App {


  /*
    Using all this infrustructure and fraims implement handler for SignUp operation
    1) validation, using rules declare in scala doc above SignUpRequest. All validation errors must be gathered together
    with help of ValidationError
    2) implement mapper for converting it to common SignUpDto and hash password
    3) implement processor(only simple stub which immediately returns OK answer)
    Write test for validator and mapper components
   */

  val error1: ValidationError = null

  val error2: ValidationError = null

  val composedError: Error = error1 + error2

  composedError.errorMessage == "errorMessage : [ field1,field2,....], errorMessage2: [field3,field4]"

}




