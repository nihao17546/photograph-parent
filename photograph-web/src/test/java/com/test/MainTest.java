package com.test;

import com.nihaov.photograph.common.utils.BaseUtil;
import com.nihaov.photograph.common.utils.HttpClientUtils;
import com.nihaov.photograph.common.utils.RedisUtil;
import com.nihaov.photograph.dao.IDataDAO;
import com.nihaov.photograph.pojo.po.IMGPO;
import com.nihaov.photograph.pojo.po.ImagePO;
import com.nihaov.photograph.web.util.BaiduUtils;
import com.nihaov.photograph.web.util.QINIUUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.*;
import java.util.*;

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

    @Resource
    private IDataDAO dataDAO;
    @Resource
    private QINIUUtils qiniuUtils;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Test
//    public void sdfsfd(){
//        List<Long> exist = dataDAO.selectExist();
//        for(int i=1;i<=10;i++){
//            RowBounds rowBounds = new RowBounds((i - 1) * 5000, 5000);
//            List<IMGPO> sourceList = dataDAO.selectImgPagination(rowBounds);
//            for(IMGPO imgpo : sourceList){
//                if(exist.contains(imgpo.getId())){
//                    continue;
//                }
//                if(imgpo.getTitle().startsWith("用户上传")){
//                    continue;
//                }
//                ImagePO po = new ImagePO();
//                po.setId(imgpo.getId());
//                po.setTitle(imgpo.getTitle());
//                po.setCompressSrc(imgpo.getCompressSrc());
//                po.setWidth(imgpo.getWidth());
//                po.setHeight(imgpo.getHeight());
//                po.setCreatedAt(imgpo.getCreatedAt());
//                po.setFlag(imgpo.getFlag());
//                po.setUid(imgpo.getUid());
//                String savePath = imgpo.getSavePath()
//                        .replace("http://fdfs.nihaov.com", "/Users/nihao/mydata/ftp");
//
//                File file = null;
//                String fileName = imgpo.getSavePath().replace("http://fdfs.nihaov.com/","")
//                        .replaceAll("/","_");
//
//                CloseableHttpClient httpClient = HttpClientUtils.getHttpClient();
//                CloseableHttpResponse response = null;
//                HttpEntity entity = null;
//                try {
//                    HttpGet httpGet = new HttpGet(imgpo.getSavePath());
//                    httpGet.setHeader("Content-Type","application/x-www-form-urlencoded");
//                    HttpClientUtils.config(httpGet);
//                    response = httpClient.execute(httpGet);
//                    if(response.getStatusLine().getStatusCode() != 200){
//                        throw new RuntimeException("下载图片失败:" + response.getStatusLine().getStatusCode());
//                    }
//                    entity = response.getEntity();
//                    InputStream inputStream = entity.getContent();
//
//                    file = new File(savePath);
//                    if(!file.getParentFile().exists()){
//                        file.getParentFile().mkdirs();
//                    }
//                    FileOutputStream out = new FileOutputStream(file);
//                    byte[] buf = new byte[1024 * 8];
//                    while (true){
//                        int read = 0;
//                        read = inputStream.read(buf);
//                        if(read == -1){
//                            break;
//                        }
//                        out.write(buf, 0, read);
//                    }
//                    out.flush();
//                    out.close();
//                }catch (Exception e){
//                    file = null;
//                    logger.error(e.getMessage() + ":" + imgpo.getSavePath());
//                }finally {
//                    if(entity!=null){
//                        try {
//                            EntityUtils.consume(entity);
//                        } catch (Exception e) {
//                            logger.error("close entity error",e);
//                        }
//                    }
//                    if(response!=null){
//                        try {
//                            response.close();
//                        } catch (Exception e) {
//                            logger.error("close response error",e);
//                        }
//                    }
//                }
//
//                if(file == null){
//                    continue;
//                }
//
//                if (imgpo.getCompressSrc().contains("http://fdfs.nihaov.com")){
//                    QINIUUtils.Result result = null;
//                    try{
//                        result = qiniuUtils.upload(file, fileName, "data");
//                    }catch (Exception e){
//                        logger.error("七牛上传错误:" + e.getMessage());
//                        continue;
//                    }
//                    if(result.getRet() != 1){
//                        logger.error("七牛上传错误:" + result.getMsg());
//                        continue;
//                    }
//                    po.setSrc("http://oy3ox608v.bkt.clouddn.com/" + result.getMsg());
//                    logger.info("------upload---id:{}------", imgpo.getId());
//                }
//                else{
//                    po.setSrc(imgpo.getCompressSrc());
//                }
//                int a = dataDAO.insert(po);
//                logger.info("------insert---id:{}---a:{}", imgpo.getId(), a);
//            }
//        }
//    }

    @Test
    public void kroego(){
        for(int i=1;i<=10;i++){
            RowBounds rowBounds = new RowBounds((i - 1) * 1000, 1000);
            List<ImagePO> sourceList = dataDAO.selectImagePagination(rowBounds);
            for(ImagePO imgpo : sourceList){
                if(!imgpo.getCompressSrc().contains("http://fdfs.nihaov.com/compress")){
                    continue;
                }
                String savePath = imgpo.getCompressSrc()
                        .replace("http://fdfs.nihaov.com/compress", "/Users/nihao/mydata/ftp/compress");

                File file = null;
                String fileName = imgpo.getCompressSrc().replace("http://fdfs.nihaov.com/compress/","")
                        .replaceAll("/","_");

                CloseableHttpClient httpClient = HttpClientUtils.getHttpClient();
                CloseableHttpResponse response = null;
                HttpEntity entity = null;
                try {
                    HttpGet httpGet = new HttpGet(imgpo.getCompressSrc());
                    httpGet.setHeader("Content-Type","application/x-www-form-urlencoded");
                    HttpClientUtils.config(httpGet);
                    response = httpClient.execute(httpGet);
                    if(response.getStatusLine().getStatusCode() != 200){
                        throw new RuntimeException("下载图片失败:" + response.getStatusLine().getStatusCode());
                    }
                    entity = response.getEntity();
                    InputStream inputStream = entity.getContent();

                    file = new File(savePath);
                    if(!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buf = new byte[1024 * 8];
                    while (true){
                        int read = 0;
                        read = inputStream.read(buf);
                        if(read == -1){
                            break;
                        }
                        out.write(buf, 0, read);
                    }
                    out.flush();
                    out.close();
                }catch (Exception e){
                    file = null;
                    logger.error(e.getMessage() + ":" + imgpo.getCompressSrc());
                }finally {
                    if(entity!=null){
                        try {
                            EntityUtils.consume(entity);
                        } catch (Exception e) {
                            logger.error("close entity error",e);
                        }
                    }
                    if(response!=null){
                        try {
                            response.close();
                        } catch (Exception e) {
                            logger.error("close response error",e);
                        }
                    }
                }

                if(file == null){
                    continue;
                }
                QINIUUtils.Result result = null;
                try{
                    result = qiniuUtils.upload(file, fileName, "activity");
                }catch (Exception e){
                    logger.error("七牛上传错误:" + e.getMessage());
                    continue;
                }

                int a = dataDAO.updateCompress(imgpo.getId(), "http://ox2n31sqv.bkt.clouddn.com/" + result.getMsg());
                logger.info("------insert---id:{}---a:{}", imgpo.getId(), a);
            }
        }
    }
}
