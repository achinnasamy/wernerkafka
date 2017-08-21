package com.dmac.streams;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.ProcessorSupplier;

/**
 * Created by dharshekthvel on 4/8/17.
 */
public class DataProcessor implements Processor<String, String> {

    @Override
    public void init(ProcessorContext processorContext) {

    }

    @Override
    public void process(String key, String value) {

        System.out.println(key + value);
    }

    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }


}
