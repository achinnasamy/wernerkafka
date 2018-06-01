package com.dmac.producer

import java.util.{Arrays, Properties}

import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

/**
  * Created by dharshekthvel on 4/26/18.
  */
object UIDConsumer {

  def main(args : Array[String]) = {

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    // When changed the consumer group, it behaves as a TOPIC or QUEUE
    props.put("group.id", "AWS-CONSUMER5")
    props.put("enable.auto.commit", "true")
    //props.put("auto.commit.interval.ms", "1000")
    props.put("session.timeout.ms", "30000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

    /**
      *
      */
    val consumer = new KafkaConsumer[String, String](props)

    // Consumer consuming from a Particular Topic
    //consumer.subscribe(Arrays.asList("ZETA-ENGINE-TOPIC"))

    val partitionTwentyThree = new TopicPartition("KANDY-TOPIC", 23)
    val partitionTwentyFour = new TopicPartition("KANDY-TOPIC", 24)
    val partitionTwentyFive = new TopicPartition("KANDY-TOPIC", 25)
    consumer.assign(Arrays.asList(partitionTwentyThree,partitionTwentyFour,partitionTwentyFive))


    while (true) {

      val records = consumer.poll(1)

      records.forEach(new ForEachPrintOutputReckoner)

      consumer.commitSync()
      //consumer.commitAsync()

    }


  }


}

class ForEachPrintOutputReckoner extends java.util.function.Consumer[ConsumerRecord[String,String]] {
  override def accept(t: ConsumerRecord[String,String]): Unit = {
    println(t.key() + " - - - " + t.value())
  }
}
