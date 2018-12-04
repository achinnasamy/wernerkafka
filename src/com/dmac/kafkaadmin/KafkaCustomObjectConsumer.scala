package com.dmac.kafkaadmin

import java.io.{ByteArrayInputStream, ObjectInputStream}
import java.util.{Arrays, Properties}

import com.dmac.ForEacher
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object KafkaCustomObjectConsumer {

  def main(args : Array[String]) = {


    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    // When changed the consumer group, it behaves as a TOPIC or QUEUE
    props.put("group.id", "AWS-CONSUMER3")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("session.timeout.ms", "30000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer")

    val consumer = new KafkaConsumer[Array[Byte], Array[Byte]](props)

    // Consumer consuming from a Particular Topic
    consumer.subscribe(Arrays.asList("BLOCK-TOPIC"))

    while (true) {
      val records = consumer.poll(1)

      records.forEach(new ForEacherPrinter)


    }

  }


}


//  class ForEacherPrinter extends java.util.function.Consumer[ConsumerRecord[Array[Byte], Array[Byte]]] {
//    override def accept(t: ConsumerRecord[Array[Byte], Array[Byte]]): Unit = {
//      println(new String(t.key()) + " - - - " + new String(t.value()))
//    }
//  }


class ForEacherPrinter extends java.util.function.Consumer[ConsumerRecord[Array[Byte], Array[Byte]]] {
  override def accept(t: ConsumerRecord[Array[Byte], Array[Byte]]): Unit = {
    println(deserialise(t.key()).asInstanceOf[BlockChainDTO].data + " - - - " + deserialise(t.value()).asInstanceOf[BlockChainDTO].hash)
  }

  def deserialise(bytes: Array[Byte]): Any = {
    val ois = new ObjectInputStream(new ByteArrayInputStream(bytes))
    val value = ois.readObject
    ois.close()
    value
  }
}