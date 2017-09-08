package com.dmac;

import java.util.Properties;

/**
 * Created by dharshekthvel on 29/8/17.
 */
public class ANZConsumerRouting {

    public static void main(String args[]) {


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        // When changed the consumer group, it behaves as a TOPIC or QUEUE
        props.put("group.id", "AWS-CONSUMER2");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

     //   KafkaConsumer consumer = new KafkaConsumer<>(props);



    }
}
