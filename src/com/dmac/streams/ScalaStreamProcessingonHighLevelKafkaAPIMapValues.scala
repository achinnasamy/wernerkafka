package com.dmac.streams

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams._
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.processor.ProcessorSupplier

/**
  * Created by dharshekthvel on 5/3/18.
  */
object ScalaStreamProcessingonHighLevelKafkaAPIMapValues {

  def main(args: Array[String]) = {

    val builder = new StreamsBuilder()

    val dataStream = builder.stream[String,String]("HDFS-TOPIC")

//    val mappedDataStream = dataStream.mapValues(
//
//      new ValueMapper[String, String] {
//                          override def apply(value: String): String =
//                            value.concat("__DATA_HAS_BEEN_MAPPED___")
//      }
//
//    )

    val ibmStream = dataStream.mapValues(

      new ValueMapper[String, String] {
        override def apply(value: String): String =
          value.concat("___IBM_PROCESSED___")
      }

    )
//
//    val auaDataStream = dataStream.mapValues(
//
//      new ValueMapper[String, String] {
//        override def apply(value: String): String = {
//          println(value)
//          val columns = value.split(",")
//          columns(3)
//        }
//      }
//
//    )

//    ibmStream.to("IBM_PROCESSED_TOPIC")
//
//    mappedDataStream.to("MAPPED_HDFS_DATA_TOPIC")

    ibmStream.to("IBM-TOPIC")

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
