package com.cloud.api.bean.resp;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RobotResponseData {
    private int robotId;
    private String message;
    private Long timestamp;
}
