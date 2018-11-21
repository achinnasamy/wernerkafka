package com.dmac.superiorstreams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import java.util.Properties;

/**
 * Created by dharshekthvel on 7/8/17.
 */
public class StreamManipulationsReckoner {

    public static void main(String args[]) {

        StreamsBuilder builder = new StreamsBuilder();


        KStream data = builder.stream("Squad-Topic");

        //KStream mappedValuesData = data.mapValues(each -> each.toString().toUpperCase());
        KStream mappedData = data.map(((key, value) -> new KeyValue<>(key.toString().toUpperCase(), value.toString().toUpperCase())));

        /*

            Filter and FilterNot filters transformation of the Stream.

         */


//        KStream filteredData =  mappedData.filter((key, value) -> key.toString().startsWith("A"));
//        KStream inverseFilteredData =  mappedData.filterNot((key, value) -> key.toString().startsWith("A"));
//        filteredData.foreach((key,value) -> System.out.println("Filtered data key  " + key + " and value is " + value));
//        inverseFilteredData.foreach((key,value) -> System.out.println("Non-filtered key is " + key + " and value is " + value));


        KStream<String, String>[] branches = mappedData.branch((key, value) -> value.toString().startsWith("MESH"),
                                                               (key, value) -> value.toString().startsWith("CONTRACT"),
                                                               (key, value) -> true
                                                              );


        KStream meshData = branches[0];
        KStream contractData = branches[1];
        KStream allOtherData = branches[2];



        // Similar to foreach
//        mappedData.peek((key,value) -> System.out.println("The key is " + key + " and value is " + value));

//        mappedData.to("Squad-SVM-Model");
//
//        mappedData.through("Squad-SVM-Model")
//                  .map(((key, value) -> new KeyValue<>(key.toString().length(), value.toString().length())))
//                  .print();


        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "DataStreamJOB");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());


        KafkaStreams stream = new KafkaStreams(builder.build(), props);

        stream.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(e.getMessage());
            }
        });

        stream.start();


        //stream.close();
    }
}
