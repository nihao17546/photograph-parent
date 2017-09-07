package com.test;

import com.nihaov.photograph.common.utils.BaseUtil;
import com.nihaov.photograph.common.utils.HttpClientUtils;
import com.nihaov.photograph.common.utils.RedisUtil;
import com.nihaov.photograph.web.util.BaiduUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
//    @Qualifier("redisTemplate")
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//    @Resource
//    private RedisUtil redisUtil;
//
//    @Resource
//    private BaiduUtils baiduUtils;
//
//    @Test
//    public void test01(){
//        Long a = (Long)redisTemplate.execute(new RedisCallback() {
//            public Long doInRedis(RedisConnection connection) throws DataAccessException {
//                Long a = connection.incr(("test1").getBytes());
//                return a;
//            }
//        });
//        System.out.println(a);
//    }
//    @Test
//    public void test02(){
//        redisTemplate.execute(new RedisCallback() {
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                byte[] b = connection.get("dwedew".getBytes());
//                System.out.println("-----");
//                return null;
//            }
//        });
//    }
////    photographWebSearchKey#one
//
//    @Test
//    public void test03(){
//        final byte[][] keys = new byte[4][];
//        keys[0] = "a".getBytes();
//        keys[1] = "b".getBytes();
//        keys[2] = "c".getBytes();
//        keys[3] = "b".getBytes();
//        List<byte[]> result = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
//            @Override
//            public List<byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                return redisConnection.mGet(keys);
//            }
//        });
//        System.out.println("----");
//    }
//
//    @Test
//    public void test04(){
////        Map<String,Object> map = redisUtil.getPaiHangBange();
//        Map<String,String> map = redisUtil.mulGet("9");
//        Map<String,String> m = redisUtil.mulGet("*");
//        System.out.println("---");
//    }

    @Test
    public void baidutoken() throws Exception{
//        File file = new File("/Users/nihao/Downloads/1_jason5186.jpg");
//        String image = BaseUtil.getBase64(new FileInputStream(file));
//        String s = baiduUtils.getBaiduToken();
//        CloseableHttpClient httpClient = HttpClientUtils.getHttpClient();
//        HttpEntity reqEntity = MultipartEntityBuilder.create()
//                .addTextBody("max_face_num", "1")
//                .addTextBody("face_fields", "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities")
//                .addTextBody("access_token", s)
//                .addTextBody("image", "dasdwq,dwdw,"+image)
//                .build();
//        HttpPost httpPost = new HttpPost("https://aip.baidubce.com/rest/2.0/face/v1/detect");
//        httpPost.setEntity(reqEntity);
//        HttpClientUtils.config(httpPost);
//        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//        HttpEntity entity = httpResponse.getEntity();
//        System.out.println(EntityUtils.toString(entity));
    }

    @Test
    public void dasdasd() throws FileNotFoundException {
//        File file = new File("/Users/nihao/Downloads/mm.jpg");
//        String image = BaseUtil.getBase64(new FileInputStream(file));
//        BaiduUtils.Detect detect = baiduUtils.detect(image);
//        System.out.println("---");
    }

}
