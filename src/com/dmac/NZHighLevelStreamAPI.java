//package com.dmac;
//
//import org.apache.kafka.streams.kstream.KStreamBuilder;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.kstream.*;
//import java.util.Properties;
//
///**
// * Created by dharshekthvel on 29/8/17.
// */
//public class NZHighLevelStreamAPI {
//
//    public static void main(String args[]) {
//
//        KStreamBuilder builder = new KStreamBuilder();
//        KStream data = builder.stream(Serdes.String(), Serdes.String(), "BDAS");
//
//
//        KStream mappedStream = data.mapValues((v)-> v.toString().toUpperCase());
//
//        data.mapValues((x) -> x.toString().toLowerCase()).to("ANOTHER-TOPIC");
//
//
//        mappedStream.to("FINAL-TOPIC");
//
//        KStream<String,String>[] branches = mappedStream.branch((key,value) -> key.toString().startsWith("AUS"),
//                                                                (key,value) -> key.toString().startsWith("NEW"));
//
//
//        branches[0].to("NEW-TOPIC");
//
//
//
//
//
//
//
//
//        Properties props = new Properties();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTABLEJOB");
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//
//
//        KafkaStreams stream = new KafkaStreams(builder, props);
//        stream.start();
//
//    }
//}
