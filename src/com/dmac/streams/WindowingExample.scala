package com.dmac.streams

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream._
import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization._
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsConfig}

/**
  * Created by dharshekthvel on 19/8/17.
  */
object WindowingExample {


  def main(args : Array[String]) = {

    val builder = new KStreamBuilder
    val alerts: KStream[String, String] = builder.stream(Serdes.String(), Serdes.String(), "");
    val incidents : KStream[String, String]  = builder.stream(Serdes.String(), Serdes.String(), "");

//    alerts.outerJoin(incidents,
//      new JoinClasser,
//      JoinWindows.of(TimeUnit.SECONDS.toMillis(5)),
//      Serdes.String(), Serdes.String(), Serdes.String())

    val props = new Properties
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTABLEJOB")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)


    val stream = new KafkaStreams(builder, props)
    stream.start()

  }

}

//class JoinClasser[String,String] extends ValueJoiner[String,String] {
//  override def apply(value1: String, value2: String): String = ""
//}
