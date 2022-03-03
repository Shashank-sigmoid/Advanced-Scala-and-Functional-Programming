package functional_programming_exercises

object advanced_pattern_matching extends App {

  object even {
    def unapply(arg: Int): Boolean = arg % 2 == 0
  }

  object odd {
    def unapply(arg: Int): Boolean = arg % 2 == 1
  }

  object singleDigit {
    def unapply(arg: Int): Boolean = arg >= -9 && arg <= 9
  }

  val n: Int = 5
  val test = n match {
    case singleDigit() => "A single digit"
    case even() => "An even number"
    case odd() => "An odd number"
    case _ => "No property"
  }
  println(test)
}
