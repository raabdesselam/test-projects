package com.test.offers

import com.typesafe.scalalogging.slf4j.LazyLogging

import scala.collection.SortedSet

/**
  * To keep trace for best offers from the first to the last
  *
  * @param bestChildrenIds : Ids of offers that give a max importance
  * @param maxImportance   : The max importance found
  */
case class OffersMaxImportance(
                                bestChildrenIds: List[Int],
                                maxImportance: Double
                              )

/**
  *
  * @param id         : id of the offer
  * @param importance : importance of the offer
  * @param nextOffers : next 4 offers
  */
case class Offer(
                  id: Int,
                  importance: Double,
                  var nextOffers: SortedSet[Offer] = SortedSet.empty(Ordering.fromLessThan[Offer](_.importance > _.importance))
                ) {

  def addRefToNextOffer(child: Offer): Unit = {
    nextOffers = nextOffers + child
  }

  def maxImportance(): OffersMaxImportance = {

    if (nextOffers.isEmpty) {
      OffersMaxImportance(List(id), importance)
    }
    else {
      val bestOffer = nextOffers
        .map {
          _.maxImportance()
        }
        .maxBy {
          _.maxImportance
        }

      bestOffer.copy(bestChildrenIds = id :: bestOffer.bestChildrenIds,
        maxImportance = bestOffer.maxImportance + importance
      )

    }

  }

}


object OffersBuilder extends LazyLogging {

  def build(values: List[Int]): Option[Offer] = {

    val offers = values
      .zipWithIndex
      .map { case (importance, id) => Offer(id, importance) }

    for (offerIndex <- offers.indices) {
      logger.debug(s"processing offer at index $offerIndex with value ${offers(offerIndex)}")

      for (childrenOffersIndices <- offerIndex + 1 until Math.min(offerIndex + 4, offers.length)) {
        logger.debug(s"adding offer at index $childrenOffersIndices  with value with value ${offers(childrenOffersIndices)}" +
          s" to offer at index $offerIndex")
        offers(offerIndex).addRefToNextOffer(offers(childrenOffersIndices))
      }
    }

    offers.headOption

  }

}
