package com.dmac.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

/**
 * Created by dharshekthvel on 25/8/17.
 */
public class JavaStreamProcessingonHighLevelKafkaAPI {

    public static void main(String args[]) {

        StreamsBuilder builder = new StreamsBuilder();
        KStream data = builder.stream("HDFS-TOPIC");

        Topology topology = builder.build();

        KStream mappedStream = data.mapValues((v)-> v.toString().concat("__HIGH_LEVEL_DATA_CLEANED__"));

        //data.mapValues((x) -> x.toString().toLowerCase()).to("DATA-CLEANSED-TOPIC");

        mappedStream.to("HIGH-TOPIC");

        KStream<String,String>[] branches = mappedStream.branch((key,value) -> key.toString().startsWith("AUS"),
                                                                (key,value) -> key.toString().startsWith("NEW"));


        branches[0].to("NEW-TOPIC");

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTABLEJOB");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        StreamsConfig config = new StreamsConfig(props);

        KafkaStreams stream = new KafkaStreams(topology, config);

        stream.start();



    }
}
