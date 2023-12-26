package net.study.functional.lesson_14_futures

import net.study.functional.lesson_14_futures.MyThreadFactory.counter

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class MyThreadFactory(threadPrefixName: String) extends ThreadFactory {

  override def newThread(r: Runnable): Thread = {

    val t = new Thread(Thread.currentThread().getThreadGroup, r, s"$threadPrefixName-${counter.getAndIncrement()}")
    if (t.isDaemon) t.setDaemon(false)
    t
  }
}

object MyThreadFactory {

  private val counter = new AtomicInteger(0)

  def apply(threadPrefixName: String): MyThreadFactory = new MyThreadFactory(threadPrefixName)

}
