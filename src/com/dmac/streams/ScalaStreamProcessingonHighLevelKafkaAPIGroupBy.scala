package com.dmac.streams

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams._
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.state.Stores

/**
  * Created by dharshekthvel on 5/3/18.
  */
object ScalaStreamProcessingonHighLevelKafkaAPIGroupBy {

  def main(args: Array[String]) = {

    val builder = new StreamsBuilder()

    val dataStream = builder.stream[String,String]("HDFS-TOPIC")

    val countedStore = Stores.inMemoryKeyValueStore("COUNTED-STORE")

    val materializedStore =   Materialized.as(countedStore)
                   .withKeySerde(Serdes.String())
                   .withValueSerde(Serdes.Long())
                   .withCachingDisabled()



¡
    val redisGroupedDataStream = dataStream.groupBy(new KeyValueMapper[String, String, String]() {
      override def apply(key: String, word: String): String = word
    })

    /**
      * GroupByKey
      */
    val keyGroupedStream = dataStream.groupByKey()


    keyGroupedStream.count(materializedStore)

    //redisGroupedDataStream.count(materializedStore).toStream.to("REDIS_DATA_TOPIC")

    val topology = builder.build()

    val props = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "HIGH-LEVEL-JOB")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName())
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName())
    val config = new StreamsConfig(props)

    // Print the topology
    println(topology.describe())

    val stream = new KafkaStreams(topology, config)


    stream.start()

  }

}
