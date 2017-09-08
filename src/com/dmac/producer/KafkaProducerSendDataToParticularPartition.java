package com.dmac.producer;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * Created by dharshekthvel on 25/8/17.
 */
public class KafkaProducerSendDataToParticularPartition {

    public static void main(String args[]) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "AWS-ID");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", "1");
        props.put("batch.size","445");
        props.put("compression.codec","1");

        KafkaProducer producer = new KafkaProducer<String, String>(props);

        ProducerRecord data = new ProducerRecord<String, String>("VEH-TOPIC", 1,"key_1", "data ingested to parition 1");

        producer.send(data);

        producer.flush();

    }
}
