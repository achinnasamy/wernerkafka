package com.dmac.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;

import java.util.Properties;

/**
 * Created by dharshekthvel on 19/8/17.
 */
public class KTableProcessing {

    public static void main(String args[]) {


        StreamsBuilder builder = new StreamsBuilder();


        KTable data = builder.table("KTABLE-TOPIC");

//        data.toStream().foreach((x,y) -> System.out.println("Key = " + x + " Value" + y));

        // Operations supported by KTable
        /*
        data.filter()
                data.filterNot()
                        data.groupBy()
                                data.join()
                                        data.leftJoin()
                                                data.outerJoin()
                                                        data.through()
                                                                data.to();
        data.toStream()
        */

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "KTABLEJOB");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 1000L);


        KafkaStreams stream = new KafkaStreams(builder.build(), props);

        stream.start();

    }
}
