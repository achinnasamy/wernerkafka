package com.dmac.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by dharshekthvel on 25/8/17.
 */
public class KafkaConsumerConsumeDataFromAPartition {

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


        KafkaConsumer consumer = new KafkaConsumer<String, String>(props);

        TopicPartition partition2 = new TopicPartition("VEH-TOPIC", 10);
        consumer.assign(Arrays.asList(partition2));

        while (true) {
            ConsumerRecords records = consumer.poll(1);

            records.forEach(new ForEacheDataIteration());

        }




    }
}

class ForEacheDataIteration implements java.util.function.Consumer<ConsumerRecord<String, String>> {
    @Override
    public void accept(ConsumerRecord<String, String> input) {
        System.out.println(input.key() + " --- " + input.value());
    }
}