package net.study

import java.io.Closeable

package object functional {

  implicit class RichEither[L, R](either: Either[L, R]) {

    def orElse[L1 >: L, R1 >: R](alternative: => Either[L1, R1]): Either[L1, R1] = if (either.isRight) either else alternative

  }

  def usingSource[A, R <: Closeable](closeable: R)(body: R => A): A = try body(closeable) finally closeable.close()

  def usingSourceWithFinally[A, R <: Closeable](closeable: R)(body: R => A)(finallyBlock: => Unit): A =
    try body(closeable) finally {
      finallyBlock
      closeable.close()
    }


}
