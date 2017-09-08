package com.dmac

import java.util.{Arrays, Properties}

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._

/**
  * Created by dharshekthvel on 29/8/17.
  *
  * Consumer - subscribe and unsubscribe at the live consuming
  */
object ANZConsumer {

  def main(args: Array[String]) = {

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    // When changed the consumer group, it behaves as a TOPIC or QUEUE
    props.put("group.id", "AWS-CONSUMER2")
    props.put("enable.auto.commit", "false")
    props.put("auto.commit.interval.ms", "1000")
    props.put("session.timeout.ms", "30000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")

    val consumer = new KafkaConsumer[String, String](props)
    //consumer.subscribe(Arrays.asList("DATA-OCEAN-TOPIC"))

    val partitionZero = new TopicPartition("DATA-OCEAN-TOPIC", 0)
    consumer.assign(Arrays.asList(partitionZero))


    while (true) {
      val records = consumer.poll(1)


      for (record <- records.asScala) {

        if (record.key().equals("UNSUBSCRIBE")) {
          consumer.unsubscribe()
          consumer.subscribe(Arrays.asList("TOPIC-ANZ"))
        }
        else
          println(" Printing the key and value - key - " + record.key() + " value - " + record.value())


      }
    }


  }
}

class ForEacherPrint extends java.util.function.Consumer[ConsumerRecord[String,String]] {
  override def accept(t: ConsumerRecord[String,String]): Unit = {
    println(t.key() + " - - - " + t.value())
  }
}