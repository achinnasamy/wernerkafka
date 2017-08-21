package com.dmac.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.KStreamBuilder;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

/**
 * Created by dharshekthvel on 19/8/17.
 */
public class GlobalKTableProcessing {

    public static void main(String args[]) {


        KStreamBuilder builder = new KStreamBuilder();


        GlobalKTable data = builder.globalTable(Serdes.String(), Serdes.String(),"Squad-Topic");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        KafkaStreams stream = new KafkaStreams(builder, props);

        stream.start();
    }
}
