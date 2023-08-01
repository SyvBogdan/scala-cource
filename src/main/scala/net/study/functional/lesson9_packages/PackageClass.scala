package net.study.functional.lesson9_packages

class PackageClass {

  private[this] val privateThisProperty = "privateProperty"

  val primaryClass = new PrimaryClass

  println(primaryClass.privatePackageProperty)

}
