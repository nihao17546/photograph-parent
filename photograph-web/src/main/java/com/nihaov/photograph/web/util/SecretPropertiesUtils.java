package com.nihaov.photograph.web.util;

import com.nihaov.photograph.dao.ISecretDAO;
import com.nihaov.photograph.pojo.po.SecretPO;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Created by nihao on 17/9/5.
 */
@Component
public class SecretPropertiesUtils {
    private static String accessKey = null;
    private static String secretKey = null;
    private static String appId = null;
    private static String appSecret = null;

    @Resource
    private ISecretDAO secretDAO;
    @PostConstruct
    public void init() {
        SecretPO accessKeyPO = secretDAO.selectByName("七牛accessKey");
        SecretPO secretKeyPO = secretDAO.selectByName("七牛secretKey");
        SecretPO appIdPO = secretDAO.selectByName("微信appId");
        SecretPO appSecretPO = secretDAO.selectByName("微信appSecret");
        accessKey = accessKeyPO.getValue().trim();
        secretKey = secretKeyPO.getValue().trim();
        appId = appIdPO.getValue().trim();
        appSecret = appSecretPO.getValue().trim();
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
}
