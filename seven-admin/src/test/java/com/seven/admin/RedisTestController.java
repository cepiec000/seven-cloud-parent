package com.seven.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author v_chendongdong
 * @version 1.0
 * @description TODO
 * @date 2021/1/8 17:49
 */
@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = {AdminApplication.class})
public class RedisTestController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test() {
        redisTemplate.opsForValue().set("cdd","123123");
        System.out.println("----------------------");
    }
}
