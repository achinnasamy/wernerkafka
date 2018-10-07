package com.dmac

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.io.Source

object ReadingAFile {

  def main(args : Array[String]) = {


    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "UNIQUE-PRODUCER_ID_3")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("linger.ms", "1")
    props.put("batch.size","445")

    val producer = new KafkaProducer[String, String](props)

    val bufferedSource = Source.fromFile("/Users/dharshekthvel/ac/data/auth.csv")

    for (each <- bufferedSource.getLines()) {

      println(each)
      val data = new ProducerRecord[String, String]("ZETA-TOPIC","zeon", each)
      producer.send(data)

    }

    producer.flush()
    producer.close()

  }
}
