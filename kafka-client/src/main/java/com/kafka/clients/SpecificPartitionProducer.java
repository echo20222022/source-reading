package com.kafka.clients;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 将消息发送到指定的分区.
 * */
public class SpecificPartitionProducer {

    public void sendToSpecificPartition() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        for (int i = 0;i < 5000;i ++) {
            //将消息发送到0号分区
            producer.send(new ProducerRecord<>("test", 0, "", "test-" + i), new Callback() {
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
        SpecificPartitionProducer producer = new SpecificPartitionProducer();
        producer.sendToSpecificPartition();
    }
}
