package com.nihaov.photograph.spider.util;

import com.nihaov.photograph.common.utils.HttpClientUtils;
import com.nihaov.photograph.pojo.po.SpiderImgPO;
import com.nihaov.photograph.spider.model.HProxy;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by nihao on 18/1/25.
 */
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static String get(String url, Map<String,String> headers, HProxy hProxy) throws HttpException{
        logger.info("------------------------获取html[{}]-----------------------------", url);
        CloseableHttpResponse httpResponse = null;
        HttpEntity entity = null;
        try{
            CloseableHttpClient httpClient = HttpClientUtils.getHttpClient();
            HttpGet httpGet = new HttpGet(url);
            if(headers != null){
                for(String key : headers.keySet()){
                    String value = headers.get(key);
                    if(!"".equals(value)){
                        httpGet.setHeader(key, value);
                    }
                }
            }
            httpGet.setConfig(getConfig(hProxy, 10000, 10000));
            httpResponse = httpClient.execute(httpGet);
            int code = httpResponse.getStatusLine().getStatusCode();
            if(code != 200){
                throw new RuntimeException("响应状态码:" + code);
            }
            entity = httpResponse.getEntity();
            String resultStr = EntityUtils.toString(entity, "utf-8");
            return resultStr;
        }catch (Exception e){
//            logger.error("http get请求失败,url[{}],错误信息[{}]", url, e.getMessage());
            throw new HttpException("GET请求[" + url + "]失败,代理[" + (hProxy == null ? "无" : hProxy.toString()) + "]", e);
        }finally {
            if(httpResponse != null){
                try {
                    httpResponse.close();
                } catch (Exception e) {
                    logger.error("close CloseableHttpResponse error", e);
                }
            }
            if(entity != null){
                try {
                    EntityUtils.consume(entity);
                } catch (Exception e) {
                    logger.error("close HttpEntity error", e);
                }
            }
            logger.info("------------------------获取html[{}]操作完成-----------------------------", url);
        }
    }

    public static RequestConfig getConfig(HProxy hProxy, Integer connectTimeout, Integer socketTimeout){
        RequestConfig.Builder builder= RequestConfig.custom();
        if(hProxy != null){
            builder.setProxy(new HttpHost(hProxy.getHost(), hProxy.getPort()));
        }
        if(socketTimeout!=null){
            builder.setSocketTimeout(socketTimeout);
        }
        if(connectTimeout!=null){
            builder.setConnectTimeout(connectTimeout);
        }
        return builder.build();
    }

    public static SpiderImgPO download(String src, String savePath, String userAgent){
        logger.info("------------------------开始下载图片[{}]-----------------------------", src);
        long start = System.currentTimeMillis();
        URLConnection conn = null;
        for(int i = 1; i <= 5; i++){
            URL ur = null;
            try {
                ur = new URL(src);
                conn = ur.openConnection();
                conn.setDoOutput(true);
                conn.setRequestProperty("User-Agent", userAgent);
                conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch, br");
                conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
                conn.setRequestProperty("Connection", "keep-alive");
                break;
            } catch (Exception e) {
                if(i == 5){
                    logger.error("下载图片失败,src:"+src+",错误信息:"+e.getClass().getName() + " " + e.getMessage());
                    break;
                }
            }
        }
        if(conn != null){
            File saveFile = new File(savePath);
            if(!saveFile.getParentFile().exists()){
                saveFile.getParentFile().mkdirs();
            }
            try(BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveFile))){
                byte[] temp = new byte[819200];
                int count = 0;
                while((count = bis.read(temp)) != -1){
                    bos.write(temp, 0, count);
                    bos.flush();
                }
                SpiderImgPO imgPO = new SpiderImgPO();
                imgPO.setSavePath(savePath);
                imgPO.setSrc(src);
                logger.info("---------------------图片[{}]下载成功,耗时{}秒---------------------",
                        src, (System.currentTimeMillis()-start)/1000);
                return imgPO;
            }catch (Exception e){
                logger.error("下载图片失败,src:{},错误信息:{}",
                        src, e.getClass().getName() + " " + e.getMessage());
                if(saveFile.exists()){
                    saveFile.delete();
                }
            }
        }
        return null;
    }

    public static String getTypeStr(String src){
        int index;
        if((index = src.lastIndexOf("."))!=-1&&
                (src.substring(index).equalsIgnoreCase(".png")||
                        src.substring(index).equalsIgnoreCase(".PNG")||
                        src.substring(index).equalsIgnoreCase(".jif")||
                        src.substring(index).equalsIgnoreCase(".GIF")||
                        src.substring(index).equalsIgnoreCase(".JPEG"))){
            return src.substring(index);
        }
        else{
            return ".jpeg";
        }
    }

}
