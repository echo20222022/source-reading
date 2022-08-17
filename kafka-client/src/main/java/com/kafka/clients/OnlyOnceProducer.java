package com.kafka.clients;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 精准发送一次
 * */
public class OnlyOnceProducer {

    public void send() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig. VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        properties.put(ProducerConfig.ACKS_CONFIG, "-1");
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction_id_0");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //初始化事务
        producer.initTransactions();
        //开启事务
        producer.beginTransaction();
        try {
            for (int i = 0; i < 100; i++) {
                producer.send(new ProducerRecord<>("test", "test--" + i));
            }
            //int i = 10 / 0;
            //提交事务
            producer.commitTransaction();
        }catch (Exception ex) {
            System.out.println("发送异常，终止事务");
            //终止事务
            producer.abortTransaction();
        } finally {
            producer.close();
        }

    }

    public static void main(String[] args) {
        OnlyOnceProducer producer = new OnlyOnceProducer();
        producer.send();
    }
}
