package net.study.functional.lesson10_OOP_classes.hometask.oop.response

import net.study.functional.lesson10_OOP_classes.hometask.oop.dto.SignUpDto
import net.study.functional.lesson10_OOP_classes.hometask.oop.processor.Status


case class SignUpResponse(status: Status, dto: SignUpDto)

case class SignInResponse()

case class WorkInfoResponse()
