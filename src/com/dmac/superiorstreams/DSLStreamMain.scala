package com.dmac.superiorstreams

import org.apache.kafka.streams.kstream.{ForeachAction, KStream, KStreamBuilder}
import org.apache.kafka.common.serialization.Serdes
import java.util.{Properties, UUID}

import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}

/**
  * Created by dharshekthvel on 4/8/17.
  */
object DSLStreamMain {

  def main(args : Array[String]) : Unit = {


    val builder = new KStreamBuilder


    val data : KStream[String, String] = builder.stream(Serdes.String(), Serdes.String(),"BINT")


    data.foreach(new Printer)
    data.print()
    data.writeAsText("")


    val props = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)


    val stream = new KafkaStreams(builder, props)
    stream.start()
  }
}

class Printer extends ForeachAction[String,String]{
  override def apply(key: String, value: String): Unit = {
    println(s"The key is $key and value is $value")
  }
}
