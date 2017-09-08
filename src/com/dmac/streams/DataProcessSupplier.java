package com.dmac.streams;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.state.KeyValueStore;

/**
 * Created by dharshekthvel on 4/8/17.
 */
public class DataProcessSupplier implements ProcessorSupplier<String, String> {
    @Override
    public Processor<String, String> get() {
        return new DataProcessor();
    }
}


class CapitalToLengthSupplier implements ProcessorSupplier<String, String> {
    @Override
    public Processor<String, String> get() {
        return new LetterToLengthProcessor();
    }
}

class LetterToLengthProcessor implements Processor<String, String> {


    ProcessorContext processorContext = null;
    private KeyValueStore<String, String> kvStore = null;

    @Override
    public void init(ProcessorContext _processorContext) {

        this.processorContext = _processorContext;
        kvStore = (KeyValueStore) _processorContext.getStateStore("LENGTH_STORE");

        // call the punctuate
        this.processorContext.schedule(1000);

    }

    @Override
    public void process(String key, String value) {

        // Do complex processing and forward it to next topic
        processorContext.forward(Integer.toString(key.length()), Integer.toString(value.length()));
        kvStore.put(Integer.toString(key.length()), Integer.toString(value.length()));
    }

    @Override
    public void punctuate(long l) {
        processorContext.commit();
    }

    @Override
    public void close() {

        kvStore.close();
    }


}