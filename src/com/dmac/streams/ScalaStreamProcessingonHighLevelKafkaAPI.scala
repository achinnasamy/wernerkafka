package com.dmac.streams

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams._
import org.apache.kafka.streams.kstream._
import org.apache.kafka.common.serialization._
import org.apache.kafka.streams.processor.ProcessorSupplier
/**
  * Created by dharshekthvel on 5/3/18.
  */
object ScalaStreamProcessingonHighLevelKafkaAPI {

  def main(args: Array[String]) = {

    val builder = new StreamsBuilder()

    val dataStream = builder.stream[String,String]("HDFS-TOPIC")



    dataStream.mapValues(new ValueMapper[String, String] {
                          override def apply(value: String): String =
                            value.toLowerCase
                        }

    )


    dataStream.map[String, String] {
      new KeyValueMapper[String, String, KeyValue[String, String]] {
        override def apply(key: String, value: String): KeyValue[String, String] = {
          new KeyValue(key, value)
        }
      }
    }


    /*************************************************************************************************************************/
    /**
      * branch() of
      */

    dataStream.branch()

    dataStream.branch(new Predicate[String, String] {
      override def test(key: String, value: String): Boolean = value.isEmpty
    })

    dataStream.branch(

      new Predicate[String, String] {
      override def test(key: String, value: String): Boolean = value.isEmpty
      },
      new Predicate[String, String] {
        override def test(key: String, value: String): Boolean = value.isEmpty
      }
    )

    dataStream.branch(

      new Predicate[String, String] {
        override def test(key: String, value: String): Boolean = value.isEmpty
      },
      new Predicate[String, String] {
        override def test(key: String, value: String): Boolean = value.isEmpty
      },
      new Predicate[String, String] {
        override def test(key: String, value: String): Boolean = value.isEmpty
      }
    )
    /*************************************************************************************************************************/

    dataStream.filter(new Predicate[String, String] {
      override def test(key: String, value: String): Boolean = value.isEmpty
    })

    dataStream.filterNot(new Predicate[String, String] {
      override def test(key: String, value: String): Boolean = value.isEmpty
    })


//    import scala.collection.JavaConverters._
//    dataStream.flatMapValues[String]( new ValueMapper[String, java.lang.Iterable[java.lang.String]]() {
//      override def apply(value: String): java.lang.Iterable[java.lang.String] = {
//              value.toLowerCase.split("\\W+").toIterable.asJava
//      }
//    })
    // Works only with Scala 2.12
    //dataStream.flatMapValues((value: String) =>  value.toLowerCase.split("\\W+").toIterable.asJava)

    dataStream.foreach(new ForeachAction[String,String] {
      override def apply(key: String, value: String) = println(key + value)
    })

    dataStream.groupBy(new KeyValueMapper[String, String, String]() {
      override def apply(key: String, word: String): String = word
    })

    /**
      * GroupByKey
      */
    dataStream.groupByKey()

    dataStream.join(dataStream, new ValueJoiner[String,String,String] {
      override def apply(value1: String, value2: String): String = ""
    }, JoinWindows.of(1000))

    //dataStream.leftJoin()

    // Merge one datastream with another datastream
    dataStream.merge(dataStream)

    //dataStream.outerJoin()

    dataStream.peek(new ForeachAction[String,String] {
      override def apply(key: String, value: String) = println(key + value)
    })

    dataStream.process( new ProcessorSupplier[String, String] {
      override def get() = new ElasticSearchProcessor
    })

//    dataStream.selectKey(new KeyValueMapper[String, String, KeyValue[String, String]] {
//      override def apply(key: String, value: String): KeyValue[String, String] = {
//        new KeyValue(key, value)
//      }
//    })

    dataStream.through("NEW_TOPIC")

    dataStream.transform(new TransformerSupplier[String, String, KeyValue[String, String]] {
      override def get() = new RedisTransformer
    })

    //dataStream.transformValues()


    val topology = builder.build()

    val props = new Properties()
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "HIGH-LEVEL-JOB")
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName())
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName())

    // Print the topology
    println(topology.describe())

    val stream = new KafkaStreams(topology, props)

    stream.start()

  }

}
