package org.scalawithcats.chapter3


import cats.Functor
import cats.Show
import cats.instances.int._
import cats.syntax.functor._
import cats.syntax.show._

object BranchingOutWithFunctors {
  sealed trait Tree[+A]
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]

  object Tree {
    implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
      override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
        //case Branch(l, r) => Branch(map(l)(f), map(r)(f))
        case Branch(l, r) => Branch(l.map(f), r.map(f))
        case Leaf(v)      => Leaf(f(v))
      }
    }

    // We have the problem of invariance where compiler can find a Functor[Tree]
    // but not a Functor[Branch] or Functor[Leaf], so we create smart constructores
    // to compensate:
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] = Branch(left, right)
    def leaf[A](value: A): Tree[A] = Leaf(value)

    implicit def treeShow[A](implicit s: Show[A]) : Show[Tree[A]] = Show.fromToString

    //implicit def boxShow[A](implicit show: Show[A]): Show[Box[A]] = Show.fromToString
  }

  def main(args: Array[String]): Unit = {
    import Tree._
    val t1 = branch(leaf(10), leaf(20))
    val t2 = t1.map( _ * 2)
    println(s"${t2.show}")
  }
}
