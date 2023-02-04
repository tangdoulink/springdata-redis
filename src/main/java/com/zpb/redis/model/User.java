package com.zpb.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author       pengbo.zhao
 * @description  user-model
 * @createDate   2022/3/4 10:44
 * @updateDate   2022/3/4 10:44
 * @version      1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String name;

    private Integer age;

}
