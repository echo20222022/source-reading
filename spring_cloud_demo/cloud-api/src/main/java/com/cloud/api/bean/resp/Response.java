package com.cloud.api.bean.resp;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private int code;
    private boolean success;
    private String desc;
    private T data;
}
