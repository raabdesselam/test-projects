package org.scalawithcats.chapter3

import cats.Functor

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
object FunctorTest {


  def main(args: Array[String]): Unit = {


    val list = List(1, 2, 3).
      map(n => n + 1).
      map(n => n * 2).
      map(n => n + "!")


    println(list)

    val future: Future[String] =
      Future(123).
        map(n => n + 1).
        map(n => n * 2).
        map(n => n + "!")

    println(Await.result(future, 1.second))

    import cats.instances.function._ // for Functor
    import cats.syntax.functor._ // for map
    import scala.language.higherKinds

    val func1: Int => Double =
      (x: Int) => x.toDouble

    val func2: Double => Double =
      (y: Double) => y * 2

    val rMap =(func1 map func2)(1) // composition using map

    println(s"rMap",rMap)

    val rComp = (func1 andThen func2)(1) // composition using andThen

    println(s"rComp ",rComp )

    val func =
      ((x: Int) => x.toDouble).
        map(x => x + 1).
        map(x => x * 2).
        map(x => x + "!")

    println(func(1))


    import cats.instances.list._
    import cats.instances.option._

    println(Functor[List].map(List(1,2))(_ * 2))

    val funcl : Int => Int = (x:Int) => x + 1

    val liftedFunc = Functor[Option].lift(funcl)
    println(liftedFunc(Option(4)))


    val func11 = (a: Int) => a + 1
    val func21 = (a: Int) => a * 2
    val func31 = (a: Int) => a + "!"
    val func41 = func11.map(func21).map(func31)

    println(func41(123))

    def doMath[F[_]](start:F[Int]) (implicit functor:Functor[F]): F[Int] =
      start.map(n => (n + 1 )* 2)


    println(doMath(Option(4)))
    println(doMath(List(4)))

    implicit val optionFunction:Functor[Option] =
      new Functor[Option] {
        override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa map f
      }
  }
}
