package com.dmac

import java.util.Properties
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.TopicPartition
import java.util.Arrays
import java.util.Properties
import java.util.Set

/**
  * Created by dharshekthvel on 9/8/17.
  */
object KafkaConsumer {

  def main(args : Array[String]) = {


    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    // When changed the consumer group, it behaves as a TOPIC or QUEUE
    props.put("group.id", "AWS-CONSUMER2")
    props.put("enable.auto.commit", "false")
    props.put("auto.commit.interval.ms", "1000")
    props.put("session.timeout.ms", "30000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")


    /**
      *
      */
    val consumer = new KafkaConsumer[String, String](props)

    // Consumer consuming from a Particular Topic
    consumer.subscribe(Arrays.asList("AWS"))

    /**
      * Consumer consuming from a particular partition
      */
    val partition0 = new TopicPartition("AWS", 0);
    consumer.assign(Arrays.asList(partition0))

    while (true) {
      val records = consumer.poll(1)

      records.forEach(new ForEacher)
//
//      consumer.commitAsync()
//      consumer.commitSync()



    }
  }
}

class ForEacher extends java.util.function.Consumer[ConsumerRecord[String,String]] {
  override def accept(t: ConsumerRecord[String,String]): Unit = {
    println(t.key())
  }
}
