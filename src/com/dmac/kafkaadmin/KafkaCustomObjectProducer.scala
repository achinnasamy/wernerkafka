package com.dmac.kafkaadmin

import java.io.{ByteArrayOutputStream, ObjectOutputStream}
import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object KafkaCustomObjectProducer {

  def main(args : Array[String]) = {



    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "AWS-ID")
    props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")
    props.put("linger.ms", "1")
    props.put("batch.size","445")


//    val producer = new KafkaProducer[Array[Byte], Array[Byte]](props)
    //    val data = new ProducerRecord[Array[Byte], Array[Byte]]("BLOCK-TOPIC", "key_12".getBytes, "Richard Feynman 101 - thank you for your physics".getBytes)


    val blockChain = BlockChainDTO(10008885, "hashing_value")


    val producer = new KafkaProducer[Array[Byte], Array[Byte]](props)
    val data = new ProducerRecord[Array[Byte], Array[Byte]]("BLOCK-TOPIC", serialise(blockChain), serialise(blockChain))


    producer.send(data)

    producer.flush()


  }

  def serialise(value: Any): Array[Byte] = {
    val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(value)
    oos.close()
    stream.toByteArray
  }
}




case class BlockChainDTO(hash : Int, data : String)