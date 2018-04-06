package org.scalawithcats

import java.util.Date

import cats.Show
import cats.syntax.show._

object Main extends App{
  import PrintableInstances._

  override def main(args: Array[String]): Unit = {

    val vInt:Int = 5

    vInt.print

    val vString:String = "toto"

    vString.print

    val cat = Cat("toto", 2, "black")
    cat.print

    import cats.Show
    import cats.instances.int._

    val showInt: Show[Int] = Show.apply[Int]

    val intAsString: String = showInt.show(123)

    println(intAsString)

    println(112.show)

//    implicit val showDate: Show[Date] = Show.show((date: Date) => s"${date.getTime} time")

    implicit val dateShow: Show[Date] =
      new Show[Date] {
        def show(date: Date): String =
          s"${date.getTime}ms since the epoch."
      }

//    val dateShow = Show.apply[Date]
    println(new Date(0).show)
  }

}
