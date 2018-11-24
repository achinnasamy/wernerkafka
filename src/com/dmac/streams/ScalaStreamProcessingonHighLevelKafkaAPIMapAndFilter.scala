package com.dmac.streams

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams._
import org.apache.kafka.streams.kstream._

/**
  * Created by dharshekthvel on 5/3/18.
  */
object ScalaStreamProcessingonHighLevelKafkaAPIMapAndFilter {

  def main(args: Array[String]) = {

    val builder = new StreamsBuilder()

    val dataStream = builder.stream[String,String]("HDFS-TOPIC")

    val mappedDataStream = dataStream.map[String, String] {
      new KeyValueMapper[String, String, KeyValue[String, String]] {
        override def apply(key: String, value: String): KeyValue[String, String] = {
          new KeyValue(key.concat("__KEY__IS__MAPPED___"), value.concat("___VALUE___IS___MAPPED___"))
        }
      }
    }

    val redisfilteredDataStream = dataStream.filter(new Predicate[String, String] {
      override def test(key: String, value: String): Boolean = value.startsWith("redis")
    })

    val capsuleNetworkDataStream = dataStream.filterNot(new Predicate[String, String] {
      override def test(key: String, value: String): Boolean = value.startsWith("neuralnetwork")
    })

    mappedDataStream.to("MAPPED_HDFS_DATA_TOPIC")

    redisfilteredDataStream.to("REDIS_DATA_TOPIC")

    capsuleNetworkDataStream.to("CAPSULE_NETWORK_TOPIC")

    val topology = builder.build()

    val props = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "HIGH-LEVEL-JOB")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName())
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName())


    // Print the topology
    println(topology.describe())

    val stream = new KafkaStreams(topology, props)

    stream.start()

  }

}
