package com.dmac

import java.util.Properties

import kafka.message.GZIPCompressionCodec
import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

/**
  * Created by dharshekthvel on 2/8/17.
  */
object KafkaProducer {


  def main(args : Array[String]) = {



    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "AWS-ID")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("linger.ms", "1")
    props.put("batch.size","445")
    props.put("compression.codec","3")
    props.put("compression.topics","DATA-SOURCE-TOPIC,BDAS-TOPIC")


    val producer = new KafkaProducer[String, String](props)

    //val data = new ProducerRecord[String, String]("TOPIC", "key", "value")


    val data = new ProducerRecord[String, String]("AWS", "key_12", "Richard Feynman 101 - thank you for your physics")


    val today = new java.util.Date();
    today.getTime;

    val data1 = new ProducerRecord[String, String]("TOPIC", "KEY", "VALUE")
    val data2 = new ProducerRecord[String, String]("TOPIC", 100, "KEY", "VALUE")
    val data3 = new ProducerRecord[String, String]("TOPIC", "VALUE")
    val data4 = new ProducerRecord[String, String]("TOPIC", 100, today.getTime, "KEY", "VALUE")

    // Send a topic to a particular partition
    //val data = new ProducerRecord[String, String]("EWS", 0, "key_12", "Richard Feynman 101 - thank you for your physics")

    val oncallback = new Callback {
      override def onCompletion(recordMetadata: RecordMetadata, e: Exception) = {
              println(recordMetadata.offset())
              println(recordMetadata.checksum())
              println(recordMetadata.topic())
              println(recordMetadata.partition())
      }
    }


    try {

      //////////////////////////////////////////////////////////////////////////////////////////////////////
      // Non-blocking send
      //producer.send(data)
      //////////////////////////////////////////////////////////////////////////////////////////////////////

      /*
      //////////////////////////////////////////////////////////////////////////////////////////////////////
      // Blocking send with get()
      val metadata = producer.send(data).get()
      println(metadata.offset())
      println(metadata.partition())
      println(metadata.topic())
      println(metadata.serializedKeySize())
      println(metadata.serializedValueSize())
      //////////////////////////////////////////////////////////////////////////////////////////////////////
      */


      // Scala producer with a callback
      producer.send(data, oncallback)



    }
    catch {
      case ex: Exception =>{
        println(ex)
      }

    }


    // Kafka producer produces a flush method to ensure all previously sent messages have been actually completed.
    producer.flush()
    producer.close()


  }
}
