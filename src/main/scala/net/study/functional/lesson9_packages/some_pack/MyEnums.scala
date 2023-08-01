package net.study.functional.lesson9_packages.some_pack

object FirstType extends MyType{
  override def toString: String = "FirstType"
}

object SecondType extends MyType {
  override def toString: String = "SecondType"
}

trait MyType

