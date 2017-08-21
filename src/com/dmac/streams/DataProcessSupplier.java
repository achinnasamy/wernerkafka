package com.dmac.streams;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;

/**
 * Created by dharshekthvel on 4/8/17.
 */
public class DataProcessSupplier implements ProcessorSupplier<String, String> {
    @Override
    public Processor<String, String> get() {
        return new DataProcessor();
    }
}
