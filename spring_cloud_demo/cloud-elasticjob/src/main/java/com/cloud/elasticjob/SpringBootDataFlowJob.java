package com.cloud.elasticjob;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.OneOffJobBootstrap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SpringBootDataFlowJob implements DataflowJob<String> {

    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        List<String> data = new ArrayList<>();
        for (int i = 0;i < 10;i ++) {
            data.add(UUID.randomUUID().toString());
        }
        return data;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        System.out.println("ShareItem = "+ shardingContext.getShardingParameter() +" Thread = " + Thread.currentThread().getName() +" data = " + list);
        System.out.println();
    }
}
