package com.dmac.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * Created by dharshekthvel on 8/8/17.
 */
public class EchelonProducer {

    public static void main(String args[]) {


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "EWSS");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", "1");
        props.put("batch.size","445");
        props.put("compression.codec","1");

        KafkaProducer producer = new KafkaProducer<String, String>(props);

        ProducerRecord data = new ProducerRecord<String, String>("BASHAS", "sweektoend", "Richard Feynman 101 - thank you for your physics");



        producer.send(data);

        Map<MetricName, Metric> metrics = producer.metrics();
        metrics.forEach(new IteratorMetricClass());


        producer.flush();
        producer.close();
    }
}

class IteratorMetricClass implements BiConsumer<MetricName, Metric> {
    @Override
    public void accept(MetricName metricName, Metric metric) {
        System.out.println(metric.metricName().description() + " - " + metric.value() + " - " + metricName.name());
       // System.out.println(metric.metricName().group());
    }
}


