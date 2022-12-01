package com.cloud.elasticjob.task;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ElasticDataFlowJob implements DataflowJob<Object> {

    @Override
    public List<Object> fetchData(ShardingContext shardingContext) {
        List<Object> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(UUID.randomUUID().toString());
        }
        return data;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Object> list) {
        System.out.println("ShareItem = " + shardingContext.getShardingParameter() + " Thread = " + Thread.currentThread().getName() + " data = " + list);
        System.out.println();
    }
}
