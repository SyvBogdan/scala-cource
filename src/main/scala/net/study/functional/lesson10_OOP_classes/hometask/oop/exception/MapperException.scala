package net.study.functional.lesson10_OOP_classes.hometask.oop.exception

case class MapperException(errMsg: String) extends RuntimeException(errMsg)
