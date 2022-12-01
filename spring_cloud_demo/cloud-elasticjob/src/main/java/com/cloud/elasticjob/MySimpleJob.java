package com.cloud.elasticjob;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;

import java.util.concurrent.TimeUnit;

public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        String  threadName = Thread.currentThread().getName();
        try {
            switch (shardingContext.getShardingItem()) {
                case 0:
                    System.out.println(threadName + " sharding item 0");
                    TimeUnit.SECONDS.sleep(2);
                case 1:
                    System.out.println(threadName + " sharding item 1");
                    TimeUnit.SECONDS.sleep(2);
                case 2:
                    System.out.println(threadName + " sharding item 2");
                    TimeUnit.SECONDS.sleep(2);
                default:
                    System.out.println(threadName + "default");
                    TimeUnit.SECONDS.sleep(2);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ScheduleJobBootstrap(coordinatorRegistryCenter(), new MySimpleJob(),
                jobConfiguration()).schedule();
    }

    private static CoordinatorRegistryCenter coordinatorRegistryCenter() {
        CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(
                new ZookeeperConfiguration("localhost:2181", "my-simple-job")
        );
        registryCenter.init();
        return registryCenter;
    }

    private static JobConfiguration jobConfiguration() {
        return JobConfiguration.newBuilder("MyJob", 3)
                .cron("0/5 * * * * ?").build();
    }
}
