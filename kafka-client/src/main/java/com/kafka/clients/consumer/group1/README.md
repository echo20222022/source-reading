kafka_client kafka-topics.sh --bootstrap-server localhost:9092 --describe --t
opic queue
Topic: queue	TopicId: 6_eHEheiT62ZNTwohUeiEA	PartitionCount: 3	ReplicationFactor: 2  	Configs: segment.bytes=1073741824
Topic: queue	Partition: 0	Leader: 1	Replicas: 0,1	Isr: 1,0
Topic: queue	Partition: 1	Leader: 0	Replicas: 0,1	Isr: 0,1
Topic: queue	Partition: 2	Leader: 1	Replicas: 1,0	Isr: 0,1



topic: queue
分区数: 3
副本数: 2

启动3个消费者，每个消费者消费一个分区


####  测试Range分区策略
kafka-topics.sh --bootstrap-server localhost:9092 --alter --topic queue --part
itions 7

consumer1 - 0,1,2
consumer2 - 3,4
consumer3 - 5,6

#### 测试RoundRobin分区策略
consumer1 - 1,4
consumer2 - 0,3,6
consumer3 - 2,5




