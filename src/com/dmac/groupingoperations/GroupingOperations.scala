package com.dmac.groupingoperations

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream._
import java.util.Properties

import org.apache.kafka.streams.scala.StreamsBuilder

/**
  * Created by dharshekthvel on 20/8/17.
  */
object GroupingOperations {


  def main(args : Array[String]) = {

//    val builder = new StreamsBuilder
//    val data = builder.table( "BDAS")
//
//    data.toStream.to("BTOPIC")
//
//    val ktableS =  builder.table( "BDAS")
//
//
//    val countTable = data.toStream.groupByKey.count()
//
//    val groupedStream:KGroupedStream[String, String] = data.toStream.groupByKey()
//
//
//    val countAgg  = groupedStream.count()
//
//    // count always returns a KTable
//    //countAgg is of type KTable[String, Long]
//    //
//
//    //groupedStream.count(TimeWindows.of(1000).advanceBy(1000))
//
//    countAgg.toStream.foreach(new PrintAllData)
//
//
//
//
//    val props = new Properties
//    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTABLEJOB")
//    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
//    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
//    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
//    //props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 1000")
//
//    val stream = new KafkaStreams(builder, props)
//    stream.start()
  }
}


class PrintAllData[String, Long] extends ForeachAction[String, Long] {
  override def apply(key: String, value: Long): Unit = {

    println("Key " + key + " Value " + value)
  }
}