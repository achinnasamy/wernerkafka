package com.dmac.producer;



import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;
import java.util.Set;

/**
 * Created by dharshekthvel on 8/8/17.
 */
public class EchelonConsumer {

    public static void main(String args[]) {

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");

        // When changed the consumer group, it behaves as a TOPIC or QUEUE
        props.put("group.id", "ECHELON-CONSUMER2");

        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("ATOS2-TOPIC"));



        while(true) {

            ConsumerRecords<String, String> records = consumer.poll(1);

            boolean shouldConsumerBeClosed = false;


            for (ConsumerRecord<String, String> record : records) {

                Set<TopicPartition> partitions = consumer.assignment();

                String formattedText = String.format("Key = %s  - Value is %s - Offset is %s - Partition is %s - Partitions size = %s ", record.key(),
                                                                                                                    record.value(),
                                                                                                                    record.offset(),
                                                                                                                    record.partition(),
                                                                                                                    partitions.size());




                System.out.println(formattedText);

                if (record.key().equals("seektobeginning"))
                    consumer.seekToBeginning(partitions);


                if (record.key().equals("seektoend"))
                    consumer.seekToEnd(partitions);


                if (record.key().equals("pause"))
                    consumer.pause(partitions);


                if (record.key().equals("resume"))
                    consumer.resume(partitions);


                // Unsubscribe the consumer from listening to current topic and subscribe to a new  topic
                if (record.key().equals("unsubscribe")) {
                    consumer.unsubscribe();
                    consumer.subscribe(Arrays.asList(record.value()));
                }


                // Closing and coming out clean from a consumer
                if (record.key().equals("exit"))
                    shouldConsumerBeClosed = true;

            }


            if (shouldConsumerBeClosed) {
                consumer.close();
                break;
            }
        }

    }
}
