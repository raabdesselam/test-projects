package org.scalawithcats

import java.util.Date

import cats.Show

trait Printable[A] {

  def toString(value:A):String

}

object PrintableInstances {

  implicit val IntPrintable = new Printable[Int] {

    override def toString(value: Int): String = value.toString

  }

  implicit val StringPrintable = new Printable[String] {

    override def toString(value: String): String = value

  }

  implicit val CatPrintable = new Printable[Cat] {

    override def toString(value: Cat): String = s"${value.name} is a ${value.age} year-old ${value.color} cat"

  }

  implicit class ImplicitPrintable[A](value:A) {

    def print(implicit pr:Printable[A]):Unit= {
      println(pr.toString(value))
    }


  }



}