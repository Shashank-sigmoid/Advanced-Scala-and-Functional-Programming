package functional_programming_exercises

object currying_paf extends App {

  /** Exercise #1
   * add7: Int => Int = y => 7 + y
   * as many different implementations of add7 using the above
   */
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x: Int)(y: Int) = x + y

  val add7 = (x: Int) => simpleAddFunction(7, x)          // simplest
  val add7_2 = simpleAddFunction.curried(7)
  val add7_3 = curriedAddMethod(7) _                      // PAF
  val add7_4 = curriedAddMethod(7)(_)                     // PAF = alternative syntax
  val add7_5 = simpleAddMethod(7, _: Int)                 // alternative syntax for turning methods into function values
  // y => simpleAddMethod(7, y)
  val add7_6 = simpleAddFunction(7, _: Int)               // works as well

  /** Exercise #2
     Process a list of numbers and return their string representations with different formats
     Use the %4.2f, %8.6f and %14.12f with a curried formatter function.
   */
  def curriedFormatter(s: String)(number: Double): String = s.format(number)
  val numbers = List(Math.PI, Math.E, 1, 9.8, 1.3e-12)

  val simpleFormat = curriedFormatter("%4.2f") _          // lift
  val seriousFormat = curriedFormatter("%8.6f") _
  val preciseFormat = curriedFormatter("%14.12f") _

  println("Simple Format:")
  println(numbers.map(simpleFormat))
  println("Serious Format:")
  println(numbers.map(seriousFormat))
  println("Precise Format")
  println(numbers.map(preciseFormat))
  println("Precise Format using eta-expansion:")
  println(numbers.map(curriedFormatter("%14.12f")))       // compiler does sweet eta-expansion for us

  /** Exercise #3
     Difference between
      - functions vs methods
      - parameters: by-name vs 0-lambda
   */
  def byName(n: => Int) = n + 1
  def byFunction(f: () => Int) = f() + 1

  def method: Int = 42
  def parenMethod(): Int = 42

  /**
    Calling byName and byFunction
    - int
    - method
    - parenMethod
    - lambda
    - PAF
   */
  byName(23)                                    // ok
  byName(method)                                // ok
  byName(parenMethod())
  byName(parenMethod)                           // Scala 2: ok but beware ==> byName(parenMethod());
                                                // Scala 3 forbids calling the method with no parens
  //  byName(() => 42)                          // not ok
  byName((() => 42)())                          // ok
  //  byName(parenMethod _)                     // not ok

  //  byFunction(45)                            // not ok
  //  byFunction(method)                        // not ok! compiler does not do ETA-expansion!
  byFunction(parenMethod)                       // ok, compiler does ETA-expansion
  byFunction(() => 46)                          // ok
  byFunction(parenMethod _)                     // ok, but warning- unnecessary
}
