package com.dmac.javapc;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;
import java.util.function.Consumer;

/**
 * Created by dharshekthvel on 26/10/17.
 */
public class UnisysJavaKafkaConsumer {

    public static void main(String args[]) {


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "GROUP-3");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        /**
         *
         */
        KafkaConsumer consumer = new KafkaConsumer(props);

        // Consumer consuming from a Particular Topic
        //consumer.subscribe(Arrays.asList("REDIS-TOPIC"));

        //consumer.seekToBeginning();
        //consumer.seekToEnd();

        TopicPartition partition0 = new TopicPartition("REDIS-TOPIC", 11);
        consumer.assign(Arrays.asList(partition0));

        while (true) {
            ConsumerRecords records = consumer.poll(1);

            records.forEach(new ForEachConsolePrinter());

            consumer.commitSync();
            consumer.commitAsync();


        }

    }
}

class ForEachConsolePrinter implements Consumer {

    public void accept(Object input) {

        ConsumerRecord cr = (ConsumerRecord)input;
        System.out.println(cr.key() + " - - - " + cr.value());
    }
}