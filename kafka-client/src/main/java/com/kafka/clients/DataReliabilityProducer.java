package com.kafka.clients;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 保证数据可靠性的Producer
 * acks=-1
 * partition num >= 3
 * */
public class DataReliabilityProducer {

    public void send() throws Exception {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //高可用设置
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        for (int i = 0;i < 100;i ++) {
            String bigMsg = "message-";
            producer.send(new ProducerRecord<String, String>("test", bigMsg + i), new Callback() {
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

        TimeUnit.SECONDS.sleep(3);
    }

    public static void main(String[] args) throws Exception {
        DataReliabilityProducer producer = new DataReliabilityProducer();
        producer.send();
    }
}
