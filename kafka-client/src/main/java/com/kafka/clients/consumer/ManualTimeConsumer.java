package com.kafka.clients.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * 从指定的时间开始下消费
 * */
public class ManualTimeConsumer {

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

        HashMap<TopicPartition, Long> timestampToSearch = new HashMap<>();
        // 封装集合存储，每个分区对应一天前的数据
        for (TopicPartition topicPartition : set) {
            timestampToSearch.put(topicPartition,
                    System.currentTimeMillis() - 1 * 24 * 3600 * 1000);
        }

        // 获取从 1 天前开始消费的每个分区的 offset
        Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(timestampToSearch);
        // 遍历每个分区，对每个分区设置消费时间。
        for (TopicPartition topicPartition : set) {
            OffsetAndTimestamp offsetAndTimestamp = offsets.get(topicPartition);
            // 根据时间指定开始消费的位置
            if (offsetAndTimestamp != null){
                consumer.seek(topicPartition, offsetAndTimestamp.offset());
            }
        }

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
            for (ConsumerRecord<String, String> consumerRecord : records) {
                System.out.println(consumerRecord);
            }
        }
    }
}
