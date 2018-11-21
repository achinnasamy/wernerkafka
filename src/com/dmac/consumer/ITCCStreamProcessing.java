package com.dmac.consumer;


import com.dmac.streams.DataProcessSupplier;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/**
 * Created by dharshekthvel on 17/9/17.
 */
public class ITCCStreamProcessing {


    public static void main(String args[]) {


        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        StreamsConfig config = new StreamsConfig(settings);


        Topology builder = new Topology();

        builder.addSource("Source", "ANZ-TOPIC")
                .addProcessor("CAPITAL-PROCESSOR",new DataProcessSupplier(), "Source")

                .addSink("DataSink","DATA-OCEAN-TOPIC", "CAPITAL-PROCESSOR")
                .addSink("LengthSink","DATA-LENGTH-TOPIC", "CAPITAL-LENGTH-PROCESSOR");



        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();


    }
}
