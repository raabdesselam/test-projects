package org.scalawithcats.chapter3

import cats.Functor
import cats.kernel.Monoid


object Contramap {

  final case class Box[A](value: A)

  trait Printable[A] {

    self =>

    def format(value: A): String

    def contramap[B](func: B => A): Printable[B] =
      new Printable[B] {
        def format(value: B): String =
          self.format(func(value))
      }
  }


  implicit val stringPrintable: Printable[String] = new Printable[String] {
    override def format(value: String) = {

      "\"" + value + "\""
    }
  }

  implicit val booleanPrintable: Printable[Boolean] =
    new Printable[Boolean] {
      def format(value: Boolean): String =
        if (value) "yes" else "no"
    }

//  implicit def boxPrintable[A](implicit p: Printable[A]) =
//    new Printable[Box[A]] {
//      override def format(box: Box[A]):String =
//        p.format(box.value)
//    }


  implicit def boxPrintable2[A](implicit p: Printable[A]) = p.contramap[Box[A]](_.value)


  def format[A](v: A)(implicit p: Printable[A]): String = p.format(v)

  def main(args: Array[String]): Unit = {

    println(format(true))

    println(format(Box(true)))


    import cats.Contravariant
    import cats.Show
    import cats.instances.string._


    val showString = Show[String]

    val showSymbol = Contravariant[Show].contramap(showString)( (sym:Symbol) => s"'${sym.name + sym.hashCode()}")

    println(showSymbol.show('dave))

    import cats.syntax.contravariant._

    println(showString.contramap((sym:Symbol) => s"'${sym.name + sym.hashCode()}").show('rachid))


    import cats.syntax.semigroup._
    import cats.syntax.invariant._
    implicit val symbolMonoid: Monoid[Symbol] = Monoid[String].imap(Symbol.apply)(_.name)

    Monoid[Symbol].empty

    println('a |+| 'few |+| 'words)


    import cats.instances.either._

    type F[A] = Int => A
    val functor =Functor[F]
    val either : Either[String, Int]  = Right(123)
    println(either.map(_ + 1))
  }

}
