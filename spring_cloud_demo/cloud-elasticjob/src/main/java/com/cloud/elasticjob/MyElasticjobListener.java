package com.cloud.elasticjob;

import org.apache.shardingsphere.elasticjob.infra.listener.ElasticJobListener;
import org.apache.shardingsphere.elasticjob.infra.listener.ShardingContexts;

public class MyElasticjobListener implements ElasticJobListener {
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
       String jobName = shardingContexts.getJobName();
        System.out.println("job " + jobName + " start.");
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        String jobName = shardingContexts.getJobName();
        System.out.println("job " + jobName + " end.");
    }

    @Override
    public String getType() {
        return "simpleJobListener";
    }
}
