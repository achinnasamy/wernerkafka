package com.dmac.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

/**
 * Created by dharshekthvel on 25/8/17.
 */
public class JavaStreamLowLevelKafkaAPIProcessor {

    public static void main(String args[]) {

        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        StreamsBuilder builder = new StreamsBuilder();
        Topology topology = builder.build();


        topology.addSource("Source", "HDFS-TOPIC")

                .addProcessor("DATA-CLEANSING-PROCESSOR",new HDFSDataProcessSupplier(), "Source")

                .addSink("DataCleansedSink","DATA-CLEANSED-TOPIC", "DATA-CLEANSING-PROCESSOR");


        KafkaStreams streams = new KafkaStreams(topology, settings);
        streams.start();



    }
}
