package functional_programming_exercises

object EqualityPlayground extends App {

  trait HTMLWritable {
    def toHtml: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHtml: String = s"<div>$name ($age yo) <a href=$email/> </div>"
  }
  User("John", 32, "john@rockthejvm.com").toHtml

  /**
   * Equality
   */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

  /** Exercise #1
      Implement the TC pattern for the Equality tc.
  */
  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer.apply(a, b)
  }

  val john = User("John", 32, "john@rockthejvm.com")
  val anotherJohn = User("John", 45, "anotherJohn@rtjvm.com")
  println(Equal(john, anotherJohn))

  // AD-HOC polymorphism
  /** Exercise #2
      Improve the Equal TC with an implicit conversion class
      ===(anotherValue: T)
      !==(anotherValue: T)
   */
  implicit class TypeSafeEqual[T](value: T) {
    def ===(other: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value, other)
    def !==(other: T)(implicit equalizer: Equal[T]): Boolean = ! equalizer.apply(value, other)
  }

  println(john === anotherJohn)
  john.===(anotherJohn)
  new TypeSafeEqual[User](john).===(anotherJohn)
  new TypeSafeEqual[User](john).===(anotherJohn)(NameEquality)
  /**
    TYPE SAFE
  */
  println(john == 43)                 // in Scala 2 this compiles and returns false (BAD, leads to bugs)
  //   println(john === 43)           // TYPE SAFE equality: neither Scala 2 nor Scala 3 compiles this one (best)
}
