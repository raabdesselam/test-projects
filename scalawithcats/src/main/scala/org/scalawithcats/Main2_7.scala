package org.scalawithcats

import cats.Monoid
import cats.instances.string._ // for Monoid
import cats.syntax.semigroup._ // for |+|
import cats.instances.int._

 object Main2_7 {


  def main(args: Array[String]): Unit = {

    val map1 = Map("a" -> 1, "b" -> 2)
    val map2 = Map("b" -> 3, "d" -> 4)

    import cats.instances.map._
    println(map1 |+| map2)


    import cats.instances.tuple._ // for Monoid

    val tuple1 = ("hello", 123)
    val tuple2 = ("world", 321)

    println(tuple1 |+| tuple2)

  }

}
