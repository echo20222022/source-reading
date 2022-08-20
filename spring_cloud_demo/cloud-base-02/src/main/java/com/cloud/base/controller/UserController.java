package com.cloud.base.controller;

import com.cloud.api.UserApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserApi.PREFIX)
public class UserController implements UserApi {
    @Override
    public String getUserName(Long id) {
        return "Cloud-Base-02 => zhangsan";
    }
}

