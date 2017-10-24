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
    props.put("client.id", "AWS-ID")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("linger.ms", "1")
    props.put("batch.size", "445")


    val producer = new KafkaProducer[String, String](props)

    val data = new ProducerRecord[String, String]("ANZ", 99, "key_12", "Ingesting data")
    val data1 = new ProducerRecord[String, String]("ANZ", "Ingesting data")
    val data2 = new ProducerRecord[String, String]("ANZ", "Ingesting data")


    producer.send(data, oncallback)

    producer.send(data1, oncallback)
    producer.send(data2, oncallback)


    producer.flush()

  }


  val oncallback = new Callback {
    override def onCompletion(recordMetadata: RecordMetadata, e: Exception) = {

      println(recordMetadata.partition())
      println(recordMetadata.offset())
      println(recordMetadata.topic())

    }
  }

}
