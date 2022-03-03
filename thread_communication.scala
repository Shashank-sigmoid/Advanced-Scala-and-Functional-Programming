package functional_programming_exercises

object thread_communication extends App {

  /** Exercises #1
      An example where notifyALL acts in a different way than notify
   */
  // NotifyAll
  def testNotifyAll(): Unit = {
    val bell = new Object

    (1 to 10).foreach(i => new Thread(() => {
      bell.synchronized {
        println(s"[thread $i] waiting...")
        bell.wait()
        println(s"[thread $i] is completed!")
      }
    }).start())

    new Thread(() => {
      Thread.sleep(2000)
      println("[announcer] Waiting is over!")
      bell.synchronized {
        bell.notifyAll()
      }
    }).start()
  }
  testNotifyAll()

  /** Exercise #2
      Create a deadlock
   */
  case class Friend(name: String) {
    def bow(other: Friend) = {
      this.synchronized {
        println(s"$this: I am bowing to my friend $other")
        other.rise(this)
        println(s"$this: my friend $other has risen")
      }
    }

    def rise(other: Friend) = {
      this.synchronized {
        println(s"$this: I am rising to my friend $other")
      }
    }

    var side = "right"
    def switchSide(): Unit = {
      if (side == "left") side = "right"
      else side = "left"
    }

    def pass(other: Friend): Unit = {
      while (this.side == other.side) {
        println(s"$this: Oh, but please, $other, feel free to pass...")
        switchSide()
        Thread.sleep(1000)
      }
    }
  }

  val alice = Friend("Alice")
  val bob = Friend("Bob")

  new Thread(() => bob.bow(alice)).start()                      // bob's lock,   |  then alice's lock
  new Thread(() => alice.bow(bob)).start()                      // alice's lock  |  then bob's lock

  /** Exercise #3
      Create a livestock
   */
  new Thread(() => bob.pass(alice)).start()
  new Thread(() => alice.pass(bob)).start()
}
