package com.dmac.producer

import java.util
import java.util.{Arrays, Properties}

import com.dmac.ForEacher
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer, OffsetAndMetadata, OffsetCommitCallback}
import org.apache.kafka.common.TopicPartition

object GBISConsumer {

  def main(args : Array[String]) = {



    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")

    props.put("group.id", "IBM-CONSUMER111")
    props.put("enable.auto.commit", "true")
    props.put("auto.commit.interval.ms", "1000")
    props.put("session.timeout.ms", "30000")
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")


    val consumer = new KafkaConsumer[String, String](props)
    consumer.subscribe(Arrays.asList("SLZ-TOPIC"))

    val partition0 = new TopicPartition("SLZ-TOPIC", 0);
    val partition1 = new TopicPartition("SLZ-TOPIC", 1);

    consumer.assign(Arrays.asList(partition0, partition1))




    while (true) {
      val records = consumer.poll(1)

      records.forEach(new ForEacherPrinting)

    }

    val partitionSet = consumer.assignment()


    consumer.seekToBeginning(partitionSet)

    consumer.seek(partition0, 109)

    consumer.seekToEnd(partitionSet)

    consumer.commitAsync()

    consumer.commitAsync(new ConsumerCallback())

    }

}

class ForEacherPrinting extends java.util.function.Consumer[ConsumerRecord[String,String]] {
  override def accept(t: ConsumerRecord[String,String]): Unit = {
    println(t.key() + " - - - " + t.value())
  }
}


class ConsumerCallback extends OffsetCommitCallback {
  override def onComplete(
                           mappedData: util.Map[TopicPartition, OffsetAndMetadata], e: Exception): Unit = {

    mappedData.forEach((tp,o) => o.offset())
  }
}