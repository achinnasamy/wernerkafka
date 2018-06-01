package com.dmac.producer

import java.util.Properties

import org.apache.kafka.clients.consumer.{KafkaConsumer, OffsetAndMetadata}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.{KafkaException, TopicPartition}

import scala.collection.JavaConverters._
/**
  * Created by dharshekthvel on 5/8/18.
  */
object KafkaTransactions {

  def main(args : Array[String]) = {


    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "ZETA-PRODUCER-001")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("linger.ms", "1")
    props.put("batch.size", "445")
    props.put("compression.codec", "1")
    props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "test-transactional-id")

    val producer = new KafkaProducer[String, String](props)
    val consumer = new KafkaConsumer[String, String](props)

    producer.initTransactions()


    try {
      producer.beginTransaction()

      val data = new ProducerRecord[String, String]("HDFS-TOPIC", "key_101", "Ingesting data on to OPEN_TSDB_1")
      producer.send(data)


      val offsetsToCommit = new scala.collection.mutable.HashMap[TopicPartition, OffsetAndMetadata]()

      producer.sendOffsetsToTransaction(offsetsToCommit.toMap.asJava, "")

      producer.commitTransaction()

    }
    catch {
      case ke: KafkaException => {
        print(ke.getStackTrace)

        producer.abortTransaction()
      }
    }

    producer close

  }



//  def getUncommittedOffsets(consumer: KafkaConsumer) : java.util.Map[TopicPartition, OffsetAndMetadata]  = {
//
//
//    val offsetsToCommit = new scala.collection.mutable.HashMap[TopicPartition, OffsetAndMetadata]()
//    consumer.assignment.forEach { topicPartition =>
//      offsetsToCommit.put(topicPartition, new OffsetAndMetadata(consumer.position(topicPartition)))
//    }
//    offsetsToCommit.toMap.asJava
//  }



}



