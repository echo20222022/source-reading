package com.cloud.api.bean.resp;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseData {
    private Long id;
    private String name;
    private int age;
}
