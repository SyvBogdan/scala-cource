package net.study.functional.lesson9_packages



class SubClass extends PrimaryClass {

  import some_pack.Computations

  // import some_pack.Computations._

  val subClass = new SubClass

  subClass.protectedProperty
}
