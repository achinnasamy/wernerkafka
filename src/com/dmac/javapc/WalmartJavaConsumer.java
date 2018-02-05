package com.dmac.javapc;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by dharshekthvel on 24/10/17.
 */
public class WalmartJavaConsumer {

    public static void main(String args[]) {


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        // When changed the consumer group, it behaves as a TOPIC or QUEUE
        props.put("group.id", "WALMART-CG2");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        /**
         *
         */
        KafkaConsumer consumer = new KafkaConsumer(props);

        TopicPartition partition0 = new TopicPartition("WALMART-TOPIC", 15);
        consumer.assign(Arrays.asList(partition0));


        //consumer.subscribe(Arrays.asList("WALMART-TOPIC"));


        while (true) {
            ConsumerRecords records = consumer.poll(1);


            records.forEach(new ForEachPrinter());




        }


    }
}

