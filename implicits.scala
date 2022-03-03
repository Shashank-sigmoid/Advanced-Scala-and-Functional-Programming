package functional_programming_exercises

object implicits extends App {

  /** Exercise #1
      Sort the persons by their age
   */
  case class Person(name: String, age: Int)

  val persons = List(
    Person("Bruce", 34),
    Person("Lucy", 25),
    Person("Shawn", 56),
    Person("Brett", 19)
  )

  object AlphabeticNameOrdering {
    implicit val alphabeticOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }
  object AgeOrdering {
    implicit val ageOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.age < b.age)
  }

  println("List of persons in ascending order of their names")
  import AlphabeticNameOrdering._
  for (person <- persons.sorted) {
    println(s"Name: ${person.name} | Age: ${person.age}")
  }

//  println("List of persons in ascending order of their ages")
//  import AgeOrdering._
//  for (person <- persons.sorted) {
//    println(s"Name: ${person.name} | Age: ${person.age}")
//  }

  /** Exercise #2
      TotalPrice = most used (50%)
      By unit count = 25%
      By unit price = 25%
   */
  case class Purchase(nUnits: Int, unitPrice: Double)
  object Purchase {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a,b) => a.nUnits * a.unitPrice < b.nUnits * b.unitPrice)
  }

  object UnitCountOrdering {
    implicit val unitCountOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.nUnits < _.nUnits)
  }

  object UnitPriceOrdering {
    implicit val unitPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan(_.unitPrice < _.unitPrice)
  }
}
