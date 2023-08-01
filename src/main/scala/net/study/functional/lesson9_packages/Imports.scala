package net.study.functional.lesson9_packages

import net.study.functional.lesson9_packages.some_pack.{FirstType, MyType}

import java.sql.{Date => SQLDate}
import java.util.{Date => CustomDate}

object Imports extends App {

  def computation(x: Int, y: Int)(implicit biFunc: (Int, Int) => Int) = biFunc(x, y)

  {
    import net.study.functional.lesson9_packages.some_pack.Computations.{multiply => _, sum => _, _}
    println(computation(2,3))
  }

  def showCurrentDate(date: CustomDate) = println(date)
  def showCurrentSqlDate(date: SQLDate) = println(date)

  def showType( implicit t: MyType) = println(t)

  {
    implicit val implType = FirstType

    showType
  }

  showCurrentDate(new CustomDate())


}
