//package com.dmac.streams;
//
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.processor.ProcessorSupplier;
//import org.apache.kafka.streams.processor.StateStoreSupplier;
//import org.apache.kafka.streams.state.Stores;
//
//import java.util.Properties;
//
///**
// * Created by dharshekthvel on 4/8/17.
// */
//public class ProcessorMain {
//
//    public static void main(String args[]) {
//
//
//
//        Properties settings = new Properties();
//        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB");
//        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//
//        StreamsConfig config = new StreamsConfig(settings);
//
//
//        StateStoreSupplier dataStore = Stores.create("DATA_STORE")
//                .withKeys(Serdes.String())
//                .withValues(Serdes.String())
//                .persistent()
//                .disableLogging() // disable backing up the store to a changelog topic
//                .build();
//
//
//
//        TopologyBuilder builder = new TopologyBuilder();
//        builder.addSource("Source", "BINT")
//
//                .addProcessor("Process", new DataProcessSupplier(), "Source")
//
//                .addStateStore(dataStore, "Process")
//                .addSink("Sink", "P-INTELLIGENCE", "Process");
//
//
//        KafkaStreams streams = new KafkaStreams(builder, config);
//        streams.start();
//
//
//
//    }
//}
