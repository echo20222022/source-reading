package com.cloud.api;

import com.cloud.api.bean.resp.Response;
import com.cloud.api.bean.resp.UserResponseData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

public interface UserApi {

    String PREFIX = "/api/user";

    @GetMapping("/getNameById")
    Response<UserResponseData> getUserName(@RequestParam("id") Long id);

    @PostMapping("/addUser")
    Response<Boolean> addUser();

    @GetMapping("/getUser")
    Response<Map<String, Object>> getUser();

    @GetMapping("/getReadUser")
    Response<Map<String, Object>> getReadUser();

    @GetMapping("/getWriteUser")
    Response<Map<String, Object>> getWriteUser();

    class DefaultFallback implements UserApi {
        @Override
        public Response<UserResponseData> getUserName(Long id) {
            Response<UserResponseData> response = new Response<>();
            response.setCode(200);
            response.setSuccess(false);
            response.setDesc("Internal Exception");
            return response;
        }

        @Override
        public Response<Boolean> addUser() {
            return null;
        }

        @Override
        public Response<Map<String, Object>> getUser() {
            return null;
        }

        @Override
        public Response<Map<String, Object>> getReadUser() {
            return null;
        }

        @Override
        public Response<Map<String, Object>> getWriteUser() {
            return null;
        }
    }
}
