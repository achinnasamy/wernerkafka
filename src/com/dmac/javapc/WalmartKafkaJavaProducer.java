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
public class WalmartKafkaJavaProducer {

    public static void main(String args[]) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "AWS-ID");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", "1");
        props.put("retries", Integer.MAX_VALUE);
        props.put("batch.size","445");
        props.put("request.required.acks", "-1");
        props.put("compression.codec","1");
        props.put("compression.topics","DATA-SOURCE-TOPIC,BDAS-TOPIC");


        KafkaProducer producer = new KafkaProducer(props);


        Callback oncallback = new Callback() {
            public void onCompletion(RecordMetadata recordMetadata, Exception e)
            {
                System.out.println(recordMetadata.offset());
                System.out.println(recordMetadata.checksum());
                System.out.println(recordMetadata.topic());
                System.out.println(recordMetadata.partition());
            }
        };


        ProducerRecord data = new ProducerRecord("WALMART-TOPIC", 13, "key-data", "walmart is doing good");



        producer.send(data, oncallback);
        producer.flush();

    }
}
