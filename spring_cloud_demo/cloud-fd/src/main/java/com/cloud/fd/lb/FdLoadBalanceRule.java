package com.cloud.fd.lb;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FdLoadBalanceRule implements IRule {

    private ILoadBalancer loadBalancer;
    //想要排除掉的服务提供者的端口号
    private List<Integer> excludePorts;

    public FdLoadBalanceRule() {}

    public FdLoadBalanceRule(List<Integer> excludePorts) {
        this.excludePorts = excludePorts;
    }
    @Override
    public Server choose(Object key) {
        List<Server> serverList = loadBalancer.getReachableServers();
        List<Server> avaliableServers =getAvaliableServers(serverList);

        return getAvaliableRandomServers(avaliableServers);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.loadBalancer = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    private List<Server> getAvaliableServers(List<Server> serverList) {
        if(excludePorts == null || excludePorts.size() == 0) {
            return serverList;
        }
        List<Server> aservers = new ArrayList<>();
        for (Server server : serverList) {
            boolean flag = true;
            //过滤掉指定端口的服务
            for (Integer port : excludePorts) {
                if (port == server.getPort()) {
                    flag = true;
                    break;
                }
            }

            if (flag) {
                aservers.add(server);
            }
        }
        return aservers;
    }

    private Server getAvaliableRandomServers(List<Server> serverList) {
        int idx = new Random().nextInt(serverList.size());
        return serverList.get(idx);
    }
}
