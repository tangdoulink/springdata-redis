package com.zpb.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zpb.redis.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * @author       pengbo.zhao
 * @description  
 * @createDate   2022/3/3 17:59
 * @updateDate   2022/3/3 17:59
 * @version      1.0
 */
@DisplayName("spring-data-redis-test")
@SpringBootTest(classes = RedisTemplateApplication.class)
class RedisTemplateTest {
    
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    @DisplayName("redis-string")
    void testRedisString(){
        redisTemplate.opsForValue().set("name","张三");

        Object name = redisTemplate.opsForValue().get("name");

        Assertions.assertEquals("张三",name,"获取失败");

    }


    @Test
    @DisplayName("redis-template-model-方案一")
    void testRedisStringWithModel() {

        redisTemplate.opsForValue().set("user:11",new User("张三",23));
        User user = (User) redisTemplate.opsForValue().get("user:11");

        assert user != null;
        Assertions.assertEquals("张三",user.getName(),"获取姓名失败");
        Assertions.assertEquals(23,user.getAge(),"获取年龄失败");

    }

    @Test
    @DisplayName("string-redis-template-model-方案二")
    void testStringRedisTemplateWithModel() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        User user = new User("李四", 24);
        String json = objectMapper.writeValueAsString(user);

        stringRedisTemplate.opsForValue().set("user:12",json);

        String jsonUser = stringRedisTemplate.opsForValue().get("user:12");
        User user1 = objectMapper.readValue(jsonUser, User.class);

        assert user1 != null;
        Assertions.assertEquals("李四",user1.getName(),"获取姓名失败");
        Assertions.assertEquals(24,user1.getAge(),"获取年龄失败");

    }

}
