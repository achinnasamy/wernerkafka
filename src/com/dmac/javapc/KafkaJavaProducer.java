package com.dmac.javapc;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Date;
import java.util.Properties;

/**
 * Created by dharshekthvel on 24/10/17.
 */
public class KafkaJavaProducer {

    public static void main(String args[]) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "AWS-ID");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", "1");
        props.put("batch.size","445");
        props.put("request.required.acks", "-1");
        props.put("compression.codec","1");
        props.put("compression.topics","DATA-SOURCE-TOPIC,BDAS-TOPIC");


        KafkaProducer producer = new KafkaProducer(props);

        //ProducerRecord data = new ProducerRecord("TOPIC", "key", "value")

        Date today = new java.util.Date();

        ProducerRecord data = new ProducerRecord("REDIS-TOPIC", "123key_121", "data ingestion done.");


        ProducerRecord data1 = new ProducerRecord("TOPIC", "KEY", "VALUE");
        ProducerRecord data2 = new ProducerRecord("TOPIC", 100, "KEY", "VALUE");
        ProducerRecord data3 = new ProducerRecord("TOPIC", "VALUE");
        ProducerRecord data4 = new ProducerRecord("TOPIC", 100, today.getTime(), "KEY", "VALUE");

        // Send a topic to a particular partition
        //val data = new ProducerRecord[String, String]("EWS", 0, "key_12", "Richard Feynman 101 - thank you for your physics")

        Callback oncallback = new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e)
            {
                System.out.println(recordMetadata.offset());
                System.out.println(recordMetadata.checksum());
                System.out.println(recordMetadata.topic());
                System.out.println(recordMetadata.partition());
            }
        };

        producer.send(data);
        producer.flush();

    }
}
