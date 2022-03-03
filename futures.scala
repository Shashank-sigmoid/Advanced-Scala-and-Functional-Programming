package functional_programming_exercises
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Random, Success, Try}
import scala.concurrent.duration._

// For futures
import scala.concurrent.ExecutionContext.Implicits.global

object futures extends App {

  /** Exercise #1
      Fulfill a future IMMEDIATELY with a value
      InSequence(fa, fb)
      First(fa, fb) => new future with the first value of the two futures
      Last(fa, fb) => new future with the last value
      RetryUntil[T](action: () => Future[T], condition: T => Boolean): Future[T]
   */

  // 1 - Fulfill immediately
  def fulfillImmediately[T](value: T): Future[T] = Future(value)

  // 2 - In sequence
  def inSequence[A, B](first: Future[A], second: Future[B]): Future[B] =
    first.flatMap(_ => second)

  // 3 - First out of two futures
  def first[A](fa: Future[A], fb: Future[A]): Future[A] = {
    val promise = Promise[A]
    fa.onComplete(promise.tryComplete)
    fb.onComplete(promise.tryComplete)
    promise.future
  }

  // 4 - Last out of the two futures
  def last[A](fa: Future[A], fb: Future[A]): Future[A] = {

    // 1 promise which both futures will try to complete
    // 2 promise which the LAST future will complete
    val bothPromise = Promise[A]
    val lastPromise = Promise[A]
    val checkAndComplete = (result: Try[A]) =>
      if(!bothPromise.tryComplete(result))
        lastPromise.complete(result)

    fa.onComplete(checkAndComplete)
    fb.onComplete(checkAndComplete)
    lastPromise.future
  }

  val fast = Future {
    Thread.sleep(100)
    76
  }
  val slow = Future {
    Thread.sleep(200)
    95
  }

  first(fast, slow).foreach(f => println("First: " + f))
  last(fast, slow).foreach(l => println("Last: "  +  l))
  Thread.sleep(1000)

  // Retry until
  def retryUntil[A](action: () => Future[A], condition: A => Boolean): Future[A] =
    action()
      .filter(condition)
      .recoverWith {
        case _ => retryUntil(action, condition)
      }

  val random = new Random()
  val action = () => Future {
    Thread.sleep(100)
    val nextValue = random.nextInt(100)
    println("Generated " + nextValue)
    nextValue
  }

  retryUntil(action, (x: Int) =>  x < 10).foreach(result => println("Settled at " + result))
  Thread.sleep(10000)
}
