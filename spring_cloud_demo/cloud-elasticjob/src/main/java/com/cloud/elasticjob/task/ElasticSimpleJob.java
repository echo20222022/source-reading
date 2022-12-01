package com.cloud.elasticjob.task;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ElasticSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {
        int shardingNum = shardingContext.getShardingItem();
        String shardingName = shardingContext.getShardingParameter();
        int shardingTotalCount = shardingContext.getShardingTotalCount();
        String jobName = shardingContext.getJobName();
        String taskId = shardingContext.getTaskId();

        System.out.println("当前任务分片编号 → " + shardingNum);
        System.out.println("当前任务分片名称 →" + shardingName);
        System.out.println("总的分片数 → " + shardingTotalCount);
        System.out.println("作业名称 → " + jobName);
        System.out.println("任务ID → " + taskId);
        sleep(2);

        //分片编号是从1开始的，不是0
        switch (shardingNum) {
            case 0:
                System.out.println("process sharding 0 task.");
                break;
            case 1:
                System.out.println("process sharding 1 task");
                break;
            case 2:
                System.out.println("process sharding 2 task");
                break;
            default:
                System.out.println("unknow sharding num → " + shardingNum);
        }
    }

    private void sleep(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        }catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
