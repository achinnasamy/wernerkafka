package com.dmac.producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by dharshekthvel on 8/8/17.
 */
public class MultipleConsumers {

    public static void main(String args[]) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");

        // When changed the consumer group, it behaves as a TOPIC or QUEUE
        props.put("group.id", "EWS-GROUP");

        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("EWS"));

        //List<PartitionInfo> partitions =  consumer.partitionsFor("EWS");

        Set<TopicPartition> totalPartitions = consumer.assignment();


        consumer.seekToBeginning(totalPartitions);

        while (true) {

            ConsumerRecords<String, String> records = consumer.poll(1);

            boolean shouldConsumerBeClosed = false;


            for (ConsumerRecord<String, String> record : records) {

                Set<TopicPartition> partitions = consumer.assignment();

                String formattedText = String.format("Key = %s  - Value is %s - Offset is %s - Partition is %s - Partitions size = %s ", record.key(),
                        record.value(),
                        record.offset(),
                        record.partition(),
                        partitions.size());


                consumer.unsubscribe();
                consumer.subscribe(Arrays.asList("ANZ-TOPIC"));

                System.out.println(formattedText);


            }

        }


    }

}
