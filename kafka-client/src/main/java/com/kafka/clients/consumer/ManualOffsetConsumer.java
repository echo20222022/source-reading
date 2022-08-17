package com.kafka.clients.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * 从任意指定的偏移量进行消费.
 * @author dongzhang
 * */
public class ManualOffsetConsumer {

    public void run() {
        //配置属性
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "single_consumer_group");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        List<String> topics = Arrays.asList("queue");
        consumer.subscribe(topics);

        //拿到当前的分区配置
        Set<TopicPartition> set = new HashSet<>();
        while (set.isEmpty()) {
            consumer.poll(Duration.ofSeconds(1));
            set = consumer.assignment();
        }

        //遍历所有的分区，并指定
        for (TopicPartition topicPartition : set) {
            consumer.seek(topicPartition, 1000);
        }

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> consumerRecord : records) {
                System.out.println(consumerRecord);
            }
        }
    }

    public static void main(String[] args) {
        ManualOffsetConsumer consumer = new ManualOffsetConsumer();
        consumer.run();
    }
}
