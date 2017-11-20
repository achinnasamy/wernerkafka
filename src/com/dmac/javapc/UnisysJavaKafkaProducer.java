package com.dmac.javapc;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;

import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * Created by dharshekthvel on 26/10/17.
 */
public class UnisysJavaKafkaProducer {

    public static void main(String args[]) {


        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("client.id", "AWS-ID");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("linger.ms", "1");
        props.put("retries", Integer.MAX_VALUE);
        props.put("batch.size","445");
        props.put("request.required.acks", "-1");


        KafkaProducer producer = new KafkaProducer(props);



        ProducerRecord data = new ProducerRecord("REDIS-TOPIC", 11, "ingestor_key", "data ingestion done.");


        ProducerRecord data1 = new ProducerRecord("REDIS-TOPIC", "ingestor_key", "data ingestion done.");


        ProducerRecord data2 = new ProducerRecord("REDIS-TOPIC", "data ingestion done.");


        Date today = new Date();
        ProducerRecord data3 = new ProducerRecord("REDIS-TOPIC", today.getTime(),"data ingestion done.");


        producer.send(data);

        Map<MetricName, Metric> metrics = producer.metrics();
        metrics.forEach(new IteratorMetric());

        producer.flush();


    }
}

class IteratorMetric implements BiConsumer<MetricName, Metric> {


    @Override
    public void accept(MetricName metricName, Metric metric) {

        System.out.println(metric.metricName().description() + metric.value());
    }
}
