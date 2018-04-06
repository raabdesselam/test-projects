package org.scalawithcats.chapter3

object IMap {

  case class Box[A](value: A)

  trait Codec[A] {
    def encode(value: A): String
    def decode(value: String): A
    def imap[B](dec: A => B, enc: B => A): Codec[B] = {

      val self = this

      new Codec[B] {
        override def encode(value: B) = self.encode(enc(value))

        override def decode(value: String) = dec(self.decode(value))
      }

    }
  }
  def encode[A](value: A)(implicit c: Codec[A]): String =
    c.encode(value)
  def decode[A](value: String)(implicit c: Codec[A]): A =
    c.decode(value)

  implicit val stringCodec: Codec[String] = new Codec[String] {
    override def encode(value: String) = value

    override def decode(value: String) = value

  }

  implicit val doubleCodec:Codec[Double] = stringCodec.imap(_.toDouble,_.toString)

  implicit val intCodec : Codec[Int] = stringCodec.imap(_.toInt,_.toString)

  implicit def boxConvert[A](implicit codec:Codec[A]):Codec[Box[A]] = codec.imap[Box[A]](Box[A], _.value)

  def main(args: Array[String]): Unit = {

    println(encode(1))


    println(encode(1.0))

    println(encode(Box[Int](4)))

    println(decode[Box[Double]]("123.4"))

  }
}
