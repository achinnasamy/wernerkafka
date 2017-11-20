package com.dmac.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.processor.StateStoreSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;
import org.apache.kafka.streams.state.Stores;

import java.util.Properties;

/**
 * Created by dharshekthvel on 25/8/17.
 */
public class LowLevelAPIStreamProcessing {

    public static void main(String args[]) {

        Properties settings = new Properties();
        settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB");
        settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        StreamsConfig config = new StreamsConfig(settings);

//        StateStoreSupplier dataStore = Stores.create("DATA_STORE")
//                .withKeys(Serdes.String())
//                .withValues(Serdes.String())
//                .persistent()
//                .disableLogging() // disable backing up the store to a changelog topic
//                .build();
//
//        StateStoreSupplier lengthStore = Stores.create("LENGTH_STORE")
//                .withKeys(Serdes.String())
//                .withValues(Serdes.String())
//                .persistent()
//                .disableLogging() // disable backing up the store to a changelog topic
//                .build();

        TopologyBuilder builder = new TopologyBuilder();
        builder.addSource("Source", "DATASOURCE-TOPIC")


                .addProcessor("CAPITAL-PROCESSOR",new DataProcessSupplier(), "Source")
                .addProcessor("CAPITAL-LENGTH-PROCESSOR",new CapitalToLengthSupplier(), "CAPITAL-PROCESSOR")



//                .addStateStore(dataStore, "CAPITAL-PROCESSOR")
//                .addStateStore(lengthStore, "CAPITAL-LENGTH-PROCESSOR")



                .addSink("DataSink","DATA-OCEAN-TOPIC", "CAPITAL-PROCESSOR")
                .addSink("LengthSink","DATA-LENGTH-TOPIC", "CAPITAL-LENGTH-PROCESSOR");



        KafkaStreams streams = new KafkaStreams(builder, config);
        streams.start();



    }
}
