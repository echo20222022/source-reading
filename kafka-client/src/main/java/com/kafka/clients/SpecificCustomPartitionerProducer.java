package com.kafka.clients;

import com.kafka.clients.partitioner.CustomPartitioner;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 将消息发送到指定的分区.
 * */
public class SpecificCustomPartitionerProducer {

    public void sendToSpecificPartition() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        for (int i = 0;i < 5;i ++) {
            //将消息发送到0号分区
            //分别测试  a - 0 / b - 0 / c - 0 / f - 1
            String value = "test-";
            if (i % 2 == 0) {
                value = value + "kafka";
            }else {
                value = value + "kafka";
            }
            producer.send(new ProducerRecord<>("test", "f", value), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(recordMetadata.topic() + " ");
                    stringBuilder.append(recordMetadata.partition() + " ");
                    stringBuilder.append(recordMetadata.offset() + " ");
                    stringBuilder.append(recordMetadata.timestamp() + " ");
                    System.out.println(stringBuilder.toString());
                }
            });
        }
        producer.close();
    }

    public static void main(String[] args) {
        SpecificCustomPartitionerProducer producer = new SpecificCustomPartitionerProducer();
        producer.sendToSpecificPartition();
    }
}
