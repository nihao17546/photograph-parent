package com.nihaov.photograph.spider.util;

import com.alibaba.fastjson.JSON;
import com.nihaov.photograph.common.utils.SimpleDateUtil;
import com.nihaov.photograph.dao.ISpiderDAO;
import com.nihaov.photograph.pojo.po.SpiderImgPO;
import com.nihaov.photograph.spider.constant.BaseConstant;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by nihao on 18/2/5.
 */
public class UnsplashThread implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ISpiderDAO spiderDAO;
    private String savePathPrefix;
    private static final Random rd = new Random();
    private String url = "https://unsplash.com/napi/feeds/home";
    private static String authorization = "Client-ID c94869b36aa272dd62dfaeefed769d4115fb3189a9d1ec88ed457207747be626";

    public UnsplashThread(ISpiderDAO spiderDAO, String savePathPrefix) {
        this.spiderDAO = spiderDAO;
        this.savePathPrefix = savePathPrefix;
    }

    public static void setClientID(String clientID){
        authorization = clientID;
    }
    public static String getClientID(){
        return authorization;
    }

    @Override
    public void run() {
        logger.info("----------开始任务----------");
        while (true){
            int total = 0;
            Map<String,String> headers = new HashMap<>();
            headers.put("origin","https://unsplash.com");
            headers.put("Referer","https://unsplash.com/");
            headers.put("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:58.0) Gecko/20100101 Firefox/58.0");
            headers.put("authorization",authorization);

            logger.info("----------轮循爬取[{}]----------", url);
            String str = fetch(url, headers);
            Map<String,Object> map = JSON.parseObject(str);
            if(map.containsKey("photos")){
                List<Map<String,Object>> list = (List<Map<String,Object>>) map.get("photos");
                for(Map<String,Object> obj : list){
                    Map<String,String> urls = (Map<String, String>) obj.get("urls");
                    String src = urls.get("full");
                    SpiderImgPO check = spiderDAO.selectBySrc(src);
                    if(check != null){
                        logger.info("===========忽略已存在图片{}===========", src);
                        continue;
                    }
                    Date now = new Date();
                    String savePath = savePathPrefix
                            + SimpleDateUtil.shortFormat(now).replaceAll("-", "") + "/"
                            + now.getHours() + "/"
                            + System.currentTimeMillis() + HttpUtils.getTypeStr(src);
                    SpiderImgPO spiderImgPO = HttpUtils.download(src,savePath,
                            BaseConstant.UserAgents[rd.nextInt(BaseConstant.UserAgents.length)]);
                    if(spiderImgPO != null){
                        spiderImgPO.setTitle("未知");
                        int a = spiderDAO.insert(spiderImgPO);
                        if(a == 1){
                            total ++;
                        }
                    }
                }
            }
            else{
                logger.error("没有返回photos,url[{}]", url);
            }

            logger.info("----------轮循爬取完成[{}],总共爬取{}张图片----------", url, total);

            if(map.containsKey("next_page")){
                String next_page = (String) map.get("next_page");
                url = next_page;
            }
            else{
                logger.warn("----------没有下一页内容了----------");
                break;
            }
        }
    }

    private String fetch(String url, Map<String,String> headers) {
        for(int i = 1; i <= 5; i++){
            try {
                String str = HttpUtils.get(url, headers, null);
                return str;
            } catch (HttpException e) {
                if(i == 5){
                    logger.error("网页数据抓取失败[{}]", url, e);
                    break;
                }
            }
        }
        return null;
    }
}
