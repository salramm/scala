/*
 * Scala (https://www.scala-lang.org)
 *
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package scala.collection.convert

sealed trait AccumulatorFactoryShape[A, C] {
  def factory: collection.Factory[A, C]
}

object AccumulatorFactoryShape extends LowPriorityAccumulatorFactoryShape {
  implicit val doubleAccumulatorFactoryShape: AccumulatorFactoryShape[Double, DoubleAccumulator] = new AccumulatorFactoryShape[Double, DoubleAccumulator] {
    def factory: collection.Factory[Double, DoubleAccumulator] = DoubleAccumulator
  }

  implicit val intAccumulatorFactoryShape: AccumulatorFactoryShape[Int, IntAccumulator] = new AccumulatorFactoryShape[Int, IntAccumulator] {
    def factory: collection.Factory[Int, IntAccumulator] = IntAccumulator
  }

  implicit val longAccumulatorFactoryShape: AccumulatorFactoryShape[Long, LongAccumulator] = new AccumulatorFactoryShape[Long, LongAccumulator] {
    def factory: collection.Factory[Long, LongAccumulator] = LongAccumulator
  }
}

sealed trait LowPriorityAccumulatorFactoryShape {
  implicit def anyAccumulatorFactoryShape[A]: AccumulatorFactoryShape[A, AnyAccumulator[A]] = anyAccumulatorFactoryShapePrototype.asInstanceOf[AccumulatorFactoryShape[A, AnyAccumulator[A]]]

  private val anyAccumulatorFactoryShapePrototype = new AccumulatorFactoryShape[AnyRef, AnyAccumulator[AnyRef]] {
    def factory: collection.Factory[AnyRef, AnyAccumulator[AnyRef]] = collection.IterableFactory.toFactory(AnyAccumulator)
  }
}
