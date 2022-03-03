package functional_programming_exercises

object partial_function extends App {

   /** Exercises
    1 - construct a Partial Function instance yourself (anonymous class)
    2 - Dumb chat bot as a Partial Function
   */

  val testFunction = new PartialFunction[Int, Int] {
    override def apply(x: Int): Int = x match {
      case 1 => 701
      case 2 => 345
      case 5 => 786
    }

    override def isDefinedAt(x: Int): Boolean =
      x == 1 || x == 2 || x == 5
  }
  println(testFunction(1))

  val bot: PartialFunction[String, String] = {
    case greeting @("hello" | "hi") => "Hi, my name is Jarvis, nice to meet you!"
    case bye @("goodbye" | "bye") => "Goodbye human, see you soon!"
    case "thank you" => "Your welcome! It's my pleasure to assist you."
    case _ => "I have trouble understanding you..."
  }
  scala.io.Source.stdin.getLines().map(bot).foreach(println)
}
