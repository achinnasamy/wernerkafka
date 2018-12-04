package com.dmac.producer

import java.util.Properties

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

object GBISProducer {


  def main(args : Array[String]) = {



    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "AWS-ID")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("linger.ms", "1")
    props.put("batch.size","445")

    val producer = new KafkaProducer[String, String](props)


    val data = new ProducerRecord[String, String](
             "SLZ-TOPIC",
            2,
              "data sent key",
             "data sent value")

    https://pastebin.ubuntu.com/p/ZyMc3PP7bN/

    val oncallback = new Callback {
      override def onCompletion(recordMetadata: RecordMetadata, e: Exception) = {
        println(recordMetadata.offset())
        println(recordMetadata.checksum())
        println(recordMetadata.topic())
        println(recordMetadata.partition())
      }}

    producer.send(data, oncallback)


    producer.flush()




  }
}
