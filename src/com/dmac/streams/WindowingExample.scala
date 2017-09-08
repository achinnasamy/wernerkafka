package com.dmac.streams

import java.io.File
import java.nio.file.Files

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream._
import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization._
import org.apache.kafka.streams.processor.StateStoreSupplier
import org.apache.kafka.streams.state.{QueryableStoreTypes, ReadOnlyKeyValueStore, Stores}
import org.apache.kafka.streams.{KafkaStreams, KeyValue, StreamsConfig}

/**
  * Created by dharshekthvel on 19/8/17.
  */
object WindowingExample {


  def main(args : Array[String]) = {

    val builder = new KStreamBuilder
    val alerts: KStream[String, String] = builder.stream(Serdes.String(), Serdes.String(), "ALERT-TOPIC");
    val incidents : KStream[String, String]  = builder.stream(Serdes.String(), Serdes.String(), "");

    //    alerts.outerJoin(incidents,
    //      new JoinClasser,
    //      JoinWindows.of(TimeUnit.SECONDS.toMillis(5)),
    //      Serdes.String(), Serdes.String(), Serdes.String())

    //


    //    val  countStoreSupplier = Stores.create("alert-store")
    //      .withKeys(Serdes.String())
    //      .withValues(Serdes.String())
    //      .persistent()
    //      .disableLogging() // disable backing up the store to a changelog topic
    //      .build();


    //builder.addStateStore(countStoreSupplier)
    //alerts.transform()

    alerts.map[String,String](new MapData)
      .groupByKey()
      //          .count(TimeWindows.of(TimeUnit.SECONDS.toMillis(60))
      //                            .advanceBy(TimeUnit.SECONDS.toMillis(30)), "alert-store")

      .count(TimeWindows.of(TimeUnit.SECONDS.toMillis(60)), "alert-store")

      .toStream

      .foreach(new PrinterAllData)


    val props = new Properties
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTABLEJOB")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    //props.put(StreamsConfig.APPLICATION_SERVER_CONFIG, "localhost:7070");
    val example = Files.createTempDirectory(new File("/home/dharshekthvel").toPath(), "example").toFile();
    props.put(StreamsConfig.STATE_DIR_CONFIG, example.getPath());

    val stream = new KafkaStreams(builder, props)
    stream.start()



    println("JOB Done")

  }

}

class PrinterAllData[String, Long] extends ForeachAction[String, Long] {
  override def apply(key: String, value: Long): Unit = {

    if (key.equals("PRINT-STORE"))


      println("Key " + key + " Value " + value)
  }
}

class MapData extends KeyValueMapper[String,String, KeyValue[String, String]] {

  override def apply(key: String, value: String): KeyValue[String, String] = {

    println("Mapper - " + key + " Value - " + value)

    new KeyValue(key.toString,value.toString)
  }
}