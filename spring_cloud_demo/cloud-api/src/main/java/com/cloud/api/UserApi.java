package com.cloud.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserApi {

    String PREFIX = "/api/user";

    @GetMapping("/getNameById")
    String getUserName(@RequestParam("id") Long id);

    class DefaultFallback implements UserApi {
        @Override
        public String getUserName(Long id) {
            return "unknow user";
        }
    }
}
