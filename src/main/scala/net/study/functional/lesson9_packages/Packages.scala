package net.study.functional.lesson9_packages

import net.study.functional.lesson9_packages.com.mypack.inner.myanotherPack.MyClass

object Packages  extends App{

 val x =  MyClass

}

package com.mypack {
  package inner.myanotherPack{

    case class MyClass( x :Int)

  }
}


package com.mypack2 {
  package inner.myanotherPack2{

    case class MyClass( x :Int)

  }
}
