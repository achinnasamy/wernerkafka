package com.dmac.superiorstreams

import org.apache.kafka.streams.scala.kstream.KStream
import org.apache.kafka.common.serialization.Serdes
import java.util.{Properties}

import org.apache.kafka.streams.kstream.ForeachAction
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}

/**
  * Created by dharshekthvel on 4/8/17.
  */
object ScalaStreamHighLevelKafkaAPI {

  def main(args : Array[String]) : Unit = {

    import org.apache.kafka.streams.scala.Serdes._
    import org.apache.kafka.streams.scala.ImplicitConversions._

    val builder = new StreamsBuilder()


    val dataStream : KStream[String, String] = builder.stream[String,String]("HDFS-TOPIC")

    dataStream.to("DATA-LAKE-TOPIC")

    //dataStream.foreach((x,y) => println(x+y))

    val props = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)


    val stream = new KafkaStreams(builder.build(), props)
    stream.start()
  }
}

class Printer extends ForeachAction[String,String]{
  override def apply(key: String, value: String): Unit = {
    println(s"The key is $key and value is $value")
  }
}
