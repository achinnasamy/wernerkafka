package com.dmac.streams

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.Topology
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.KStreamBuilder
/**
  * Created by dharshekthvel on 5/3/18.
  */
object ScalaStreamProcessingonHighLevelKafkaAPI {

  def main(args: Array[String]) = {

    val builder = new StreamsBuilder();
    val data = builder.stream("HDFS-TOPIC");

    val topology = builder.build();

    val props = new Properties()();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTABLEJOB");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


    val config = new StreamsConfig(props);

    val stream = new KafkaStreams(topology, config);

    stream.start();
  }

}
