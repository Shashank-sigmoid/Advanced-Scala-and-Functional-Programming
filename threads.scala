package functional_programming_exercises

object threads extends App {

  /** Exercise #1
   1) Construct 50 "inception" threads
      thread1 -> thread2 -> thread3 -> ...
      println("Hello from thread #3") in REVERSE ORDER
   */
  def inceptionThreads(maxThreads: Int, i: Int = 1): Thread = new Thread(() => {
    if (i < maxThreads) {
      val newThread = inceptionThreads(maxThreads, i + 1)
      newThread.start()
      newThread.join()
    }
    println(s"Hello from thread $i")
  })
  inceptionThreads(50).start()

  /** Exercise #2
    1) what is the biggest value possible for x? Ans: 100
    2) what is the SMALLEST value possible for x? Ans: 1
    When: thread1: x = 0
          thread2: x = 0
            ...
          thread100: x = 0
          For all threads: x = 1 and write it back to x
   */
  var x = 0
  val threads = (1 to 100).map(_ => new Thread(() => x += 1))
  threads.foreach(_.start())
  threads.foreach(_.join())
  println(x)
}
