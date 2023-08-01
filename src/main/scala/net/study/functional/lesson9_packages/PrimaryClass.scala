package net.study.functional.lesson9_packages

class PrimaryClass {

  private val privateProperty = "privateProperty"

 // private def privateProperty = "privateProperty"

  private[this] val privateThisProperty = "privateProperty"

  private[lesson9_packages] val privatePackageProperty = "privatePackageProperty"

  protected[lesson9_packages] val  protectedPackageProperty = "protectedPackageProperty"

  protected val  protectedProperty = "protectedProperty"

  protected[this] val protectedThisProperty = "protectedThisProperty"

  val publicProperty = "publicProperty"

  val primaryClass = new PrimaryClass

  primaryClass.privateProperty


}
