package com.nihaov.photograph.spider.util;

import com.alibaba.fastjson.JSON;
import com.nihaov.photograph.common.utils.SimpleDateUtil;
import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.pojo.po.SpiderImgPO;
import com.nihaov.photograph.spider.constant.BaseConstant;
import com.nihaov.photograph.spider.model.HProxy;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by nihao on 18/1/29.
 */
public class JUJUSpiderThread implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String url = "http://juju.la/blog/hot";
    private ISpiderDAO spiderDAO;
    private static final Random rd = new Random();
    private String savePathPrefix = null;

    private static List<Cookie> cookies = new ArrayList<>();
    private static List<Cookie> prefixCookies = new ArrayList<>();

    public static void resetCookie(){
        prefixCookies = new ArrayList<>();
    }

    public static void setCookie(String name, String value){
        BasicClientCookie cookie = new BasicClientCookie(name, value);
        cookie.setPath("/");
        cookie.setDomain("juju.la");
//        cookie.setExpiryDate(new Date(new Date().getTime() + 1000*60*60*24*180));
        prefixCookies.add(cookie);
    }

    public static List<String> showCookie(){
        List<String> list = new ArrayList<>();
        for(Cookie cookie : prefixCookies){
            StringBuilder sb = new StringBuilder();
            sb.append("名称:").append(cookie.getName())
                    .append(" 内容:").append(cookie.getValue());
            list.add(sb.toString());
        }
        return list;
    }

    public JUJUSpiderThread(ISpiderDAO spiderDAO, String savePathPrefix) {
        this.spiderDAO = spiderDAO;
        this.savePathPrefix = savePathPrefix;
    }

    @Override
    public void run() {
        logger.info("==============开始爬取==============");
        HProxy hProxy = HProxyFactory.create();
        bre:
        while (true){
            if(TopitSpider.build().isShutdown()){
                logger.info("==============终止操作==============");
                break bre;
            }
            logger.info("==============开始轮循==============");
            int total = 0;
            String json = handler(hProxy);
            if(json == null){
                hProxy = HProxyFactory.create();
                ThreadUtils.sleep(10);
                continue;
            }
            JUJUResponse jujuResponse = JSON.parseObject(json, JUJUResponse.class);
            List<JUJUData> dataList = jujuResponse.getData();
            if(dataList != null){
                for(JUJUData jujuData : dataList){
                    if(TopitSpider.build().isShutdown()){
                        logger.info("==============终止操作==============");
                        logger.info("==============结束轮循,爬取{}张图片==============", total);
                        break bre;
                    }
                    List<String> pictureList = jujuData.getPicturelist();
                    if(pictureList != null){
                        List<Tag> tagList = jujuData.getTagnames();
                        String title = "未命名";
                        String tag = "";
                        if(tagList != null && !tagList.isEmpty()){
                            title = tagList.get(0).getName();
                            for(Tag t : tagList){
                                tag = tag + t.getName() + ",";
                            }
                            tag = tag.substring(0, tag.length() - 1);
                        }
                        if("".equals(tag)){
                            tag = null;
                        }
                        for(String picture : pictureList){
                            SpiderImgPO check = spiderDAO.selectBySrc(picture);
                            if(check != null){
                                logger.info("===========忽略已存在图片{}===========", picture);
                                continue;
                            }
                            Date now = new Date();
                            String savePath = savePathPrefix
                                    + SimpleDateUtil.shortFormat(now).replaceAll("-", "") + "/"
                                    + now.getHours() + "/"
                                    + System.currentTimeMillis() + HttpUtils.getTypeStr(picture);
                            SpiderImgPO spiderImgPO = HttpUtils.download(picture,savePath,
                                    BaseConstant.UserAgents[rd.nextInt(BaseConstant.UserAgents.length)]);
                            if(spiderImgPO != null){
                                spiderImgPO.setTitle(title);
                                spiderImgPO.setTag(tag);
                                int a = spiderDAO.insert(spiderImgPO);
                                if(a == 1){
                                    total ++;
                                }
                            }
                        }
                    }
                }
            }
            logger.info("==============结束轮循,爬取{}张图片==============", total);
        }
    }

    private String handler(HProxy hProxy){
        CookieStore cookieStore = new BasicCookieStore();
        for(Cookie cookie : prefixCookies){
            cookieStore.addCookie(cookie);
        }
        for(Cookie cookie : cookies){
            cookieStore.addCookie(cookie);
        }
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
        HttpPost post = new HttpPost(url);
//        post.setEntity(MultipartEntityBuilder.create().addTextBody("init", "0").build());
        post.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:57.0) Gecko/20100101 Firefox/57.0");
        post.addHeader("Referer", "http://juju.la/hot");

//        post.setConfig(HttpUtils.getConfig(hProxy, 10000, 10000));

        CloseableHttpResponse response = null;
        try{
            response = httpClient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if(code != 200){
                throw new RuntimeException("响应状态码:" + code);
            }
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity, "utf-8");
            List<Cookie> cookies = cookieStore.getCookies();
            this.cookies = cookies;
            EntityUtils.consume(httpEntity);
            return result;
        }catch (Exception e){
            logger.error("handler error", e);
            return null;
        }finally {
            if(response != null){
                try {
                    response.close();
                } catch (Exception e) {
                    logger.error("close CloseableHttpResponse error");
                }
            }
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (Exception e) {
                    logger.error("close CloseableHttpClient error");
                }
            }
        }
    }

    public static class JUJUResponse{
        private Integer errorcode;
        private List<JUJUData> data;

        public Integer getErrorcode() {
            return errorcode;
        }

        public void setErrorcode(Integer errorcode) {
            this.errorcode = errorcode;
        }

        public List<JUJUData> getData() {
            return data;
        }

        public void setData(List<JUJUData> data) {
            this.data = data;
        }
    }
    public static class JUJUData{
        private List<String> picturelist;
        private List<Tag> tagnames;

        public List<String> getPicturelist() {
            return picturelist;
        }

        public void setPicturelist(List<String> picturelist) {
            this.picturelist = picturelist;
        }

        public List<Tag> getTagnames() {
            return tagnames;
        }

        public void setTagnames(List<Tag> tagnames) {
            this.tagnames = tagnames;
        }
    }
    public static class Tag{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
