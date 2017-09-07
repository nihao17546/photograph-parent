package com.nihaov.photograph.web.util;

import com.nihaov.photograph.dao.ISecretDAO;
import com.nihaov.photograph.pojo.po.SecretPO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by nihao on 17/9/5.
 */
@Component
public class SecretPropertiesUtils {
    private static String accessKey = null;
    private static String secretKey = null;
    private static String appId = null;
    private static String appSecret = null;
    private static String baiduAppId = null;
    private static String baiduSecretKey = null;

    @Resource
    private ISecretDAO secretDAO;
    @PostConstruct
    public void init() {
        Map<String,SecretPO> map = secretDAO.selectAll();
        accessKey = map.get("七牛accessKey").getValue().trim();
        secretKey = map.get("七牛secretKey").getValue().trim();
        appId = map.get("微信appId").getValue().trim();
        appSecret = map.get("微信appSecret").getValue().trim();
        baiduAppId = map.get("百度appId").getValue().trim();
        baiduSecretKey = map.get("百度secretKey").getValue().trim();
    }


    public static String getAccessKey() {
        return accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static String getAppId() {
        return appId;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getBaiduAppId() {
        return baiduAppId;
    }

    public static String getBaiduSecretKey() {
        return baiduSecretKey;
    }
}
