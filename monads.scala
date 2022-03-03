package functional_programming_exercises

object monads extends App {
  /** Exercise
    1) Implement a Lazy[T] monad = Computation which will only be executed when it's needed.
       unit/apply
       flatMap
    2) Monads = unit + flatMap
       Monads = unit + map + flatten
   */

  // 1 - Lazy monad
  class Lazy[+A](value: => A) {
    // Call by need (using private)
    private lazy val internalValue = value
    def use: A = internalValue
    def flatMap[B](f: (=> A) => Lazy[B]): Lazy[B] = f(internalValue)
  }
  object Lazy {
    def apply[A](value: => A): Lazy[A] = new Lazy(value)                      // Unit
  }

  val lazyInstance = Lazy {
    println("Steins Gate is the best anime of all time.")
    34
  }

  // To print 34 as well
  // println(lazyInstance.use)

  val flatMappedInstance = lazyInstance.flatMap(x => Lazy {
    10 * x
  })
  val flatMappedInstance2 = lazyInstance.flatMap(x => Lazy {
    10 * x
  })

  // Printed only once
  flatMappedInstance.use
  flatMappedInstance2.use

  def flatten[T](lz: Lazy[Lazy[T]]): Lazy[T] = lz.flatMap(x => x)
  /** 2 - map and flatten in terms  of flatMap
    Monad[T] {                                                              // List
      def flatMap[B](f: T => Monad[B]): Monad[B] = ... (implemented)
      def map[B](f: T => B): Monad[B] = flatMap(x => unit(f(x)))            // Monad[B]
      def flatten(m: Monad[Monad[T]]): Monad[T] = m.flatMap((x: Monad[T]) => x)
      List(1,2,3).map(_ * 2) = List(1,2,3).flatMap(x => List(x * 2))
      List(List(1, 2), List(3, 4)).flatten = List(List(1, 2), List(3, 4)).flatMap(x => x) = List(1,2,3,4)
    }
  */
}
