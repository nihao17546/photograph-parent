package com.nihaov.photograph.web.util;

import com.alibaba.fastjson.JSON;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

/**
 * Created by nihao on 17/9/5.
 */
@Component
public class QINIUUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String lookBucket = "mydata";

    public Result upload(File file, String key){
        Result result = new Result();
        Configuration configuration = new Configuration();
        UploadManager uploadManager = new UploadManager(configuration);
        Auth auth = Auth.create(SecretPropertiesUtils.getAccessKey(), SecretPropertiesUtils.getSecretKey());
        String token = auth.uploadToken(lookBucket);
        try {
            Response res = uploadManager.put(file, key, token);
            String bodyStr = res.bodyString();
            Map<String,Object> resultMap = JSON.parseObject(bodyStr);
            String k = (String) resultMap.get("key");
            result.setRet(1);
            result.setMsg(k);
        } catch (Exception e) {
            logger.error("七牛上传文件错误", e);
            result.setRet(0);
            result.setMsg("抱歉服务异常,请联系管理员:nhweiwin(微信号)");
        }
        return result;
    }

    public Result upload(String filePath, String key){
        return upload(new File(filePath), key);
    }

    public static class Result{
        private int ret;
        private String msg;

        public int getRet() {
            return ret;
        }

        public void setRet(int ret) {
            this.ret = ret;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
