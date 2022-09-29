package com.cloud.robot.controller;

import com.cloud.api.RobotApi;
import com.cloud.api.bean.resp.Response;
import com.cloud.api.bean.resp.RobotResponseData;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(RobotApi.PREFIX)
public class RobotController implements RobotApi {

    @Override
    public Response<RobotResponseData> getRobotMessage(String key) {
        RobotResponseData data = null;
        if ("0".equals(key)) {
            data = RobotResponseData.builder()
                    .robotId(1000001).message("美女").timestamp(System.currentTimeMillis())
                    .build();
        } else {
            data = RobotResponseData.builder()
                    .robotId(1000002).message("丑八怪").timestamp(System.currentTimeMillis())
                    .build();
        }
        Response<RobotResponseData> response = new Response();
        response.setCode(200);
        response.setSuccess(true);
        response.setData(data);
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception ex) {

        }
        return response;
    }

}
