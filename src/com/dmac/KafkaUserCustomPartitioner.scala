package com.dmac

import java.util

import org.apache.kafka.clients.producer.Partitioner
import org.apache.kafka.common.Cluster

/**
  * Created by dharshekthvel on 1/6/17.
  */
class KafkaUserCustomPartitioner extends Partitioner {



  override def close(): Unit = {}

  override def configure(map: util.Map[String, _]): Unit = {}

  override def partition(topic: String, key: scala.Any, keybytes: Array[Byte],
                         value: scala.Any, valuebytes: Array[Byte], cluster: Cluster): Int = {

    val key_ = key.asInstanceOf[String]
    if (key_.startsWith("M"))
      0
    else if (key_.startsWith("C"))
      6
    else
      9
  }
}
