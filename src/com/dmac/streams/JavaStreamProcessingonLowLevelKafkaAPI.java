package com.dmac.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/**
 * Created by dharshekthvel on 25/8/17.
 */
public class JavaStreamProcessingonLowLevelKafkaAPI {

    public static void main(String args[]) {

        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsConfig config = new StreamsConfig(settings);

        StreamsBuilder builder = new StreamsBuilder();
        Topology topology = builder.build();


        topology.addSource("Source", "HDFS-TOPIC")

                .addProcessor("DATA-CLEANSING-PROCESSOR",new HDFSDataProcessSupplier(), "Source")
                .addProcessor("RULE-ENGINE-PROCESSOR",new RuleEngineSupplier(), "DATA-CLEANSING-PROCESSOR")
                .addProcessor("ES-ENGINE-PROCESSOR",new ElasticSearchSupplier(), "RULE-ENGINE-PROCESSOR")

                .addSink("DataCleansedSink","DATA-CLEANSED-TOPIC", "DATA-CLEANSING-PROCESSOR")
                .addSink("RuleEngineSink","RULE-ENGINE-TOPIC", "RULE-ENGINE-PROCESSOR")
                .addSink("ESSink","ES-TOPIC", "ES-ENGINE-PROCESSOR");


        KafkaStreams streams = new KafkaStreams(topology, config);
        streams.start();



    }
}
