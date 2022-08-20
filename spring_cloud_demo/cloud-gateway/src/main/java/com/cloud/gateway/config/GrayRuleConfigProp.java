package com.cloud.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * 灰度规则配置
 * */
@Component
@ConfigurationProperties(prefix = "zuul.gray")
public class GrayRuleConfigProp {

    private List<String> users = Collections.emptyList();
    private String version;

    public List<String> getUsers() {
        return users;
    }

    public String getVersion() {
        return version;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GrayRuleConfigProp{" +
                "users=" + users +
                ", version='" + version + '\'' +
                '}';
    }
}
