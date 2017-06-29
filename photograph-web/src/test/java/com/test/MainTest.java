package com.test;

import com.nihaov.photograph.common.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by nihao on 17/6/29.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
@ActiveProfiles("test")
public class MainTest {
    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    private RedisUtil redisUtil;

    @Test
    public void test01(){
        Long a = (Long)redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long a = connection.incr(("test1").getBytes());
                return a;
            }
        });
        System.out.println(a);
    }
    @Test
    public void test02(){
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] b = connection.get("dwedew".getBytes());
                System.out.println("-----");
                return null;
            }
        });
    }
//    photographWebSearchKey#one

    @Test
    public void test03(){
        final byte[][] keys = new byte[4][];
        keys[0] = "a".getBytes();
        keys[1] = "b".getBytes();
        keys[2] = "c".getBytes();
        keys[3] = "b".getBytes();
        List<byte[]> result = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.mGet(keys);
            }
        });
        System.out.println("----");
    }

    @Test
    public void test04(){
//        Map<String,Object> map = redisUtil.getPaiHangBange();
        Map<String,String> map = redisUtil.mulGet("9");
        Map<String,String> m = redisUtil.mulGet("*");
        System.out.println("---");
    }

}
