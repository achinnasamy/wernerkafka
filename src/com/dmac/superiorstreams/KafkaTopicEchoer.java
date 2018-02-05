package com.dmac.superiorstreams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KStreamBuilder;

import java.util.Properties;

/**
 * Created by dharshekthvel on 7/8/17.
 */
public class KafkaTopicEchoer {

    public static void main(String args[]) {

        KStreamBuilder builder = new KStreamBuilder();


        KStream data = builder.stream(Serdes.String(), Serdes.String(),"KTABLE-TOPIC");


        //data.mapValues();


        data.to("DESTINATIONTOPIC");


        KStream data3 = builder.stream(Serdes.String(), Serdes.String(),"KTABLE-TOPIC");


        //data3.mapValues();


        data3.to("DESTINATIONTOPIC2");


        //data.foreach((key,value) -> System.out.println("Key = " + key + " Value = " + value));


        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "TopicPrinterJOB");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        KafkaStreams stream = new KafkaStreams(builder, props);

//        stream.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//                System.out.println(e.getMessage());
//            }
//        });

        stream.start();


        //stream.close();
    }
}
