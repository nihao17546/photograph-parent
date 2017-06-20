package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.nihaov.photograph.common.utils.DesUtil;
import com.nihaov.photograph.common.utils.WXUtil;
import com.nihaov.photograph.dao.IUserDAO;
import com.nihaov.photograph.pojo.po.UserPO;
import com.nihaov.photograph.pojo.vo.DataResult;
import com.nihaov.photograph.pojo.wx.WxUserInfo;
import com.nihaov.photograph.service.IUserService;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by nihao on 17/6/11.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IUserDAO userDAO;

    @Value("#{configProperties['appId']}")
    private String appId;
    @Value("#{configProperties['appSecret']}")
    private String appSecret;
    @Value("#{configProperties['weixinAuthUrl']}")
    private String weixinAuthUrl;

    @RequestMapping("/regist")
    @ResponseBody
    public String regist(HttpServletRequest request){
        DataResult dataResult = new DataResult();
        String nickname = request.getParameter("nickname");
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        UserPO userPO = new UserPO();
        userPO.setPassword(password);
        userPO.setNickname(nickname);
        userPO.setUserId(userId);
        try{
            userService.regist(userPO);
            dataResult.setCode(200);
            userPO.setPassword(null);
            dataResult.setResult(userPO);
        } catch (RuntimeException e){
            dataResult.setCode(500);
            dataResult.setMessage(e.getMessage());
        }
        return JSON.toJSONString(dataResult);
    }

    @RequestMapping("/auth")
    @ResponseBody
    public String auth(HttpServletRequest request){
        DataResult dataResult = new DataResult();
        String code = request.getParameter("code");
        String user = request.getParameter("user");
        String encryptedData = request.getParameter("encryptedData");
        String iv = request.getParameter("iv");
        HttpPost httpPost = new HttpPost(weixinAuthUrl);
        StringBody appid = new StringBody(appId, ContentType.create(
                "text/plain", Consts.UTF_8));
        StringBody secret = new StringBody(appSecret, ContentType.create(
                "text/plain", Consts.UTF_8));
        StringBody js_code = new StringBody(code, ContentType.create(
                "text/plain", Consts.UTF_8));
        StringBody grant_type = new StringBody("authorization_code", ContentType.create(
                "text/plain", Consts.UTF_8));
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("appid",appid)
                .addPart("secret",secret)
                .addPart("js_code",js_code)
                .addPart("grant_type",grant_type)
                .build();
        httpPost.setEntity(reqEntity);
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost)){
            HttpEntity entity = httpResponse.getEntity();
            String resultStr = EntityUtils.toString(entity, "utf-8");
            Map<String,String> result = JSON.parseObject(resultStr,Map.class);
            if(result.containsKey("openid")){
                String openid = result.get("openid");
                WxUserInfo wxUserInfo = JSON.parseObject(user,WxUserInfo.class);
                UserPO userPO = new UserPO();
                userPO.setNickname(wxUserInfo.getNickName());
                userPO.setUnionId(openid);
                userPO.setHeadPic(wxUserInfo.getAvatarUrl());
                userPO.setGender(wxUserInfo.getGender());
                UserPO rePO = userService.auth(userPO);
                dataResult.setCode(200);
                dataResult.setResult(rePO);
            }
            else if(result.containsKey("errmsg")){
                throw new RuntimeException(result.get("errmsg"));
            }
            else{
                throw new RuntimeException("授权登录失败");
            }
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMessage(e.getMessage());
        }
        return JSON.toJSONString(dataResult);
    }

    @RequestMapping("/check")
    @ResponseBody
    public String check(HttpServletRequest request){
        DataResult dataResult = new DataResult();
        String userId = request.getParameter("userId");
        UserPO userPO = userService.check(userId);
        if(userPO==null){
            dataResult.setCode(500);
        }
        else{
            dataResult.setCode(200);
            dataResult.setResult(userPO);
        }
        return JSON.toJSONString(dataResult);
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request) throws Exception {
        DataResult dataResult = new DataResult();
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        UserPO userPO = userDAO.selectByUserIdAndPassword(userId, DesUtil.encrypt(password));
        if(userPO==null){
            dataResult.setCode(500);
        }
        else{
            userPO.setPassword(null);
            userPO.setId(null);
            dataResult.setCode(200);
            dataResult.setResult(userPO);
        }
        return JSON.toJSONString(dataResult);
    }

    @RequestMapping("/favo")
    @ResponseBody
    public String favo(HttpServletRequest request) {
        DataResult dataResult = new DataResult();
        String uid = request.getParameter("uid");
        String picId = request.getParameter("picId");
        try{
            int result = userService.favo(Long.parseLong(uid),Long.parseLong(picId));
            dataResult.setCode(200);
            if(result==1){
                dataResult.setMessage("收藏成功");
            }
            else{
                dataResult.setMessage("已经收藏过了");
            }
        }catch (Exception e){
            dataResult.setCode(500);
            dataResult.setMessage("收藏失败");
        }
        return JSON.toJSONString(dataResult);
    }
}
