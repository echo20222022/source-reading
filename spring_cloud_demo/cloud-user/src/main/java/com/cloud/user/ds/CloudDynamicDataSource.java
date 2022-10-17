package com.cloud.user.ds;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CloudDynamicDataSource extends AbstractRoutingDataSource {

    //一个写数据源
    private DataSource writeDataSource;
    //多个读数据源
    private List<DataSource> readDataSource;

    private int readDataSourceSize;
    private int readDataSourceRollPattern;  //选择策略，0-随机 1-轮询
    private AtomicLong counter = new AtomicLong(0);
    private final Lock lock = new ReentrantLock();

    private static final Long MAX_POLL = Long.MAX_VALUE;

    //该方法反的是DataSource对应的Bean ID
    @Override
    protected Object determineCurrentLookupKey() {

        //获取当前的操作类型
        DynamicOperationType operationType = DynamicOperationHolder.get();

        //以下三种情况返回写库
        if (operationType == null ||
             operationType == DynamicOperationType.WRITE ||
            readDataSourceSize == 0) {
            return DynamicOperationType.WRITE.name();
        }

        int readDSIdx = 0;
        //否则返回读库
        //随机返回一个读库
        if (readDataSourceRollPattern == 0) {
            readDSIdx = ThreadLocalRandom.current().nextInt(0, readDataSourceSize);
        } else {
            //轮询

            //处理潮界的情况，将计数器归零
            long counterValue = counter.incrementAndGet();
            System.out.println("counter → " + counterValue + " readDataSourceSize → " + readDataSourceSize + " idx → " + (int)counterValue % readDataSourceSize);
            if (counterValue + 1 >= MAX_POLL) {
                try {
                    lock.lock();
                    if (counterValue + 1 > MAX_POLL) {
                        counter.set(0);
                    }
                }finally {
                    lock.unlock();
                }
            }

            readDSIdx = (int)counterValue % readDataSourceSize;
        }

        return operationType.name() + "-" + readDSIdx;
    }


    @Override
    public void afterPropertiesSet() {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DynamicOperationType.WRITE.name(), writeDataSource);
        //setTargetDataSources("READ", );
        int idx = 0;
        for (DataSource ds : getReadDataSource()) {
            dataSourceMap.put(DynamicOperationType.READ.name() + "-" + idx, ds);
            idx ++;
        }
        setTargetDataSources(dataSourceMap);
        System.out.println("dataSourceMap → " + dataSourceMap);
        super.afterPropertiesSet();
    }

    public DataSource getWriteDataSource() {
        return writeDataSource;
    }

    public void setWriteDataSource(DataSource writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

    public List<DataSource> getReadDataSource() {
        return readDataSource;
    }

    public void setReadDataSource(List<DataSource> readDataSource) {
        this.readDataSourceSize = readDataSource.size();
        this.readDataSource = readDataSource;
    }

    public void setReadDataSourceRollPattern(int readDataSourceRollPattern) {
        this.readDataSourceRollPattern = readDataSourceRollPattern;
    }
}
