package org.scalawithcats

import java.util.Date

import cats.Show
import cats.syntax.eq._
import cats.instances.int._
import cats.instances.string._
import cats.instances.option._
import cats.kernel.Eq
import cats.syntax.option._
import cats.instances.long._
import cats.syntax.show._

object Main2 {

  //Re-implement the Cat applicaঞon from the previous secঞon using Show instead
  //of Printable.
  def main(args: Array[String]): Unit = {


    //      val list = List(1, 2, 3).map(Option(_)).filter( (item:Option[Int] )=> item === Some(1):Option[Int])

    Option(1) === Option.empty[Int]

    //    println( (Some(1) : Option[Int]) === (None : Option[Int]) )

    //    println(list)

    implicit val dateShow: Show[Date] =
      new Show[Date] {
        def show(date: Date): String =
          s"${date.getTime}"
      }


    implicit val dateEq: Eq[Date] =
      Eq.instance[Date] { (date1, date2) =>

        println(date1.show, date2.show)
        date1.getTime === date2.getTime

      }

    val x = new Date() // now
    val y = new Date() // a bit later than now
    println("compare date ", x === y)

    println(1.some === none[Int])

    //equals show case class

    val cat1 = Cat("Garfield", 38, "orange and black")
    val cat2 = Cat("Heathcliff", 33, "orange and black")

    val optionCat1 = Option(cat1)
    val optionCat2 = none[Cat]

    implicit val catEq: Eq[Cat] = Eq.instance[Cat] { (cat1, cat2) =>

      cat1.color === cat2.color &&
        cat1.age === cat2.age &&
        cat1.name === cat2.name

    }

    println(cat1 === cat2)
    println(optionCat1 === optionCat2)

  }

}
