package com.nihaov.photograph.web.util;

import com.nihaov.photograph.common.utils.BaseUtil;
import com.nihaov.photograph.dao.ILoginDAO;
import com.nihaov.photograph.pojo.po.LoginPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by nihao on 17/9/7.
 */
@Component
public class LoginUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ILoginDAO loginDAO;

    public void login(HttpServletRequest request, Long uid){
        try{
            String userAgent = request.getHeader("user-agent");
            String ip = BaseUtil.getIpAddr(request);
            LoginPO loginPO = new LoginPO();
            loginPO.setUid(uid);
            loginPO.setIp(ip);
            loginPO.setUserAgent(userAgent);
            loginDAO.insert(loginPO);
        }catch (Exception e){
            logger.error("登录记录错误", e);
        }
    }
}
