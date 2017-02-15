package com.test.offers

import com.typesafe.scalalogging.slf4j.LazyLogging
import org.scalatest.{FunSuite, Matchers}

class OfferTest extends FunSuite with Matchers with LazyLogging{


  test("should find max importance"){

  val firstOffer = OffersBuilder.build(List(1, 0, -1, 3, -1, -2))

    logger.debug(s"max importance ${firstOffer.map(_.maxImportance())}")
    val result = firstOffer.get.maxImportance()
    result.bestChildrenIds should be(List(0, 3, 5))
    result.maxImportance should be(2)

  }


}
