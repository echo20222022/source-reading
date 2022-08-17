package com.kafka.clients.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 独立消费者.
 * 消费数据时，指定某个分区
 * */
public class SingleAndSpecificPartitionConsumer {

    public void pull() {

        //配置属性
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092,localhost:9093,localhost:9094");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "single_consumer_group");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        /*List<String> topics = Arrays.asList("queue");
        consumer.subscribe(topics);*/

        //指定从queue topic的第0个分区消费数据
        List<TopicPartition> topicPartitions = new ArrayList<>();
        topicPartitions.add(new TopicPartition("queue", 0));
        consumer.assign(topicPartitions);

        //拉取数据
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record);
            }
        }
    }

    public static void main(String[] args) {
        SingleAndSpecificPartitionConsumer consumer = new SingleAndSpecificPartitionConsumer();
        consumer.pull();
    }
}
