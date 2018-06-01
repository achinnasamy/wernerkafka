package com.dmac.producer

import java.util.Properties

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

/**
  * Created by dharshekthvel on 17/9/17.
  */
object UIDProducer {

  def main(args: Array[String]) = {

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "ZETA-PRODUCER-001")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("linger.ms", "1")
    props.put("batch.size", "445")
    props.put("compression.codec", "1")

    val producer = new KafkaProducer[String, String](props)

    for (i <- 1 to 10) {
      val data = new ProducerRecord[String, String]("KANDY-TOPIC", 23, "key_101", "Ingesting data on to OPEN_TSDB_1")
      //val data2 = new ProducerRecord[String, String]("KANDY-TOPIC", 26, "key_102", "Ingesting data on to Voldemort_2")
      //val data3 = new ProducerRecord[String, String]("KANDY-TOPIC", 79, "key_103", "Ingesting data on to StarDog_3")

      producer.send(data)
      producer.flush()

      
      //producer.send(data2, oncallback)
      //producer.send(data3)
    }


  }


  val oncallback = new Callback {
    override def onCompletion(recordMetadata: RecordMetadata, e: Exception) = {

      println(recordMetadata.partition())
      println(recordMetadata.offset())
      println(recordMetadata.topic())

    }
  }

}
