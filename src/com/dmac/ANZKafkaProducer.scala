package com.dmac

import java.util.Properties

                                            import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}


/**
  * Created by dharshekthvel on 28/8/17.
  */
object ANZKafkaProducer {

  def main(args : Array[String]) = {


val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "ANZKafkaProducer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("linger.ms", "1")
    props.put("batch.size","445")
    props.put("compression.codec","1")


    props.put("request.required.acks","-1")


    val producer = new KafkaProducer[String, String](props)

    val data = new ProducerRecord[String, String]("DATASOURCE-TOPIC", "data-ingestion", "BIG-DATA")
    val data1 = new ProducerRecord[String, String]("DATASOURCE-TOPIC", "BIG-DATA")
    val data2 = new ProducerRecord[String, String]("DATASOURCE-TOPIC", 2, "data-ingestion", "BIG-DATA")


      val callback = new Callback {
          override def onCompletion(recordMetadata: RecordMetadata, e: Exception) = {


              println(recordMetadata.offset())
              println(recordMetadata.topic())
              println(recordMetadata.partition())

          }
      }

    val metadata = producer.send(data, callback)

    producer.flush()


  }
}
