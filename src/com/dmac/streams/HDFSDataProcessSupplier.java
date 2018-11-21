package com.dmac.streams;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.PunctuationType;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

/**
 * Created by dharshekthvel on 4/28/18.
 */
public class HDFSDataProcessSupplier implements ProcessorSupplier<String, String> {

    @Override
    public Processor<String, String> get() {
        return new HDFSDataProcessor();
    }

}


class HDFSDataProcessor implements Processor<String, String> {


    ProcessorContext processorContext = null;
    private KeyValueStore<String, String> kvStore = null;

    @Override
    public void init(ProcessorContext _processorContext) {

        this.processorContext = _processorContext;
        //kvStore = (KeyValueStore) _processorContext.getStateStore("DATA_STORE");


        // call the punctuate
        this.processorContext.schedule(1000, PunctuationType.STREAM_TIME, (timestamp) -> {
            KeyValueIterator<String, String> iter = this.kvStore.all();
            while (iter.hasNext()) {
                KeyValue<String, String> entry = iter.next();
                processorContext.forward(entry.key, entry.value.toString());
            }
            iter.close();

            // commit the current processing progress
            processorContext.commit();
        });

    }

    @Override
    public void process(String key, String value) {

        // Do complex processing and forward it to next topic
        processorContext.forward(key.concat("__HDFS_DATA__"), value.concat("__HDFS_DATA__"));
        //kvStore.put(key.toUpperCase(),value.toUpperCase());
    }

    @Override
    public void close() {

        //kvStore.close();
    }


}



class RuleEngineSupplier implements ProcessorSupplier<String, String> {
    @Override
    public Processor<String, String> get() {
        return new RuleEngineProcessor();
    }
}

class RuleEngineProcessor implements Processor<String, String> {


    ProcessorContext processorContext = null;
    private KeyValueStore<String, String> kvStore = null;

    @Override
    public void init(ProcessorContext _processorContext) {

        this.processorContext = _processorContext;
        //kvStore = (KeyValueStore) _processorContext.getStateStore("LENGTH_STORE");

        // call the punctuate
        this.processorContext.schedule(1000, PunctuationType.STREAM_TIME, (timestamp) -> {
            KeyValueIterator<String, String> iter = this.kvStore.all();
            while (iter.hasNext()) {
                KeyValue<String, String> entry = iter.next();
                processorContext.forward(entry.key, entry.value.toString());
            }
            iter.close();

            // commit the current processing progress
            processorContext.commit();
        });

    }

    @Override
    public void process(String key, String value) {

        // Do complex processing and forward it to next topic
        processorContext.forward(key.concat("__RULE_ENGINE_DATA__"), value.concat("__RULE_ENGINE_DATA__"));

        //kvStore.put(Integer.toString(key.length()), Integer.toString(value.length()));
    }

    public void punctuate(long l) {
        processorContext.commit();
    }

    @Override
    public void close() {

        //kvStore.close();
    }


}


class ElasticSearchSupplier implements ProcessorSupplier<String, String> {
    @Override
    public Processor<String, String> get() {
        return new ElasticSearchProcessor();
    }
}

class ElasticSearchProcessor implements Processor<String, String> {


    ProcessorContext processorContext = null;
    private KeyValueStore<String, String> kvStore = null;

    @Override
    public void init(ProcessorContext _processorContext) {

        this.processorContext = _processorContext;
        //kvStore = (KeyValueStore) _processorContext.getStateStore("LENGTH_STORE");

        // call the punctuate
        this.processorContext.schedule(1000, PunctuationType.STREAM_TIME, (timestamp) -> {
            KeyValueIterator<String, String> iter = this.kvStore.all();
            while (iter.hasNext()) {
                KeyValue<String, String> entry = iter.next();
                processorContext.forward(entry.key, entry.value.toString());
            }
            iter.close();

            // commit the current processing progress
            processorContext.commit();
        });

    }

    @Override
    public void process(String key, String value) {

        // Do complex processing and forward it to next topic
        processorContext.forward(key.concat("__ELASTIC_SEARCH__"), value.concat("__ELASTIC_SEARCH__"));

        //kvStore.put(Integer.toString(key.length()), Integer.toString(value.length()));
    }

    public void punctuate(long l) {
        processorContext.commit();
    }

    @Override
    public void close() {

        //kvStore.close();
    }


}


class RedisTransformer implements Transformer<String,String,KeyValue<String, String>> {
    @Override
    public void init(ProcessorContext context) {

    }

    @Override
    public KeyValue<String, String> transform(String key, String value) {
        return null;
    }

    public KeyValue<String, String> punctuate(long timestamp) {
        return null;
    }

    @Override
    public void close() {

    }
}