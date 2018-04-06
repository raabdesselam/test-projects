package org.scalawithcats


trait Semigroup1[A] {
  def combine(x: A, y: A): A
}
trait Monoid1[A] extends Semigroup1[A] {
  def empty: A
}
object Monoid1 {
  def apply[A](implicit monoid: Monoid1[A]) =
    monoid
}


object Main3 {

  def main(args: Array[String]): Unit = {

    implicit val monoidPlusBoolean : Monoid1[Boolean]=  new Monoid1[Boolean] {
      override def empty = true

      override def combine(x: Boolean, y: Boolean) = x || y
    }

    def associativeLaw[A](x: A, y: A, z: A)
                         (implicit m: Monoid1[A]): Boolean = {
      m.combine(x, m.combine(y, z)) ==
        m.combine(m.combine(x, y), z)
    }
    def identityLaw[A](x: A)
                      (implicit m: Monoid1[A]): Boolean = {
      (m.combine(x, m.empty) == x) &&
        (m.combine(m.empty, x) == x)
    }

    // monoid OR for Boolean
    println(associativeLaw[Boolean]( true, false, false))
    println(!identityLaw[Boolean](false) )

    println()

    import cats.Monoid
    import cats.instances.string._ // for Monoid
    import cats.instances.option._
    import cats.instances.int._
    import cats.syntax.semigroup._
    import cats.syntax.option._

    //use cats Monoid
    println(Monoid[String].empty)
    println(Monoid[String].combine("hi", "toto"))


    println(Monoid[Option[Int]].combine(Option(4), Option(1)))

    println(Option(1) |+| Option(1))

    println()
    //exercices


    def add[A](items : List[A])(implicit monoid:Monoid[A]):A = {

      items.foldLeft(Monoid[A].empty)(_ |+| _)

    }

    println(add(List(1,2,3)))

    println(add(List(1,2,3).map(_.some)))


    case class Order(totalCost: Double, quantity: Double)


    implicit val orderMonoid = new Monoid[Order] {
      override def empty = Order(0,0)

      override def combine(x: Order, y: Order) = Order(x.totalCost + y.totalCost, x.quantity + y.quantity)
    }

    val order1  = Order(1.0, 5)
    val order2  = Order(2.0, 10)

    println(add(List(order1,order2)))


  }

}
