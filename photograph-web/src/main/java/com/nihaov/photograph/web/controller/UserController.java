package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.nihaov.photograph.common.utils.DesUtil;
import com.nihaov.photograph.common.utils.UTF8Utils;
import com.nihaov.photograph.common.utils.WXUtil;
import com.nihaov.photograph.dao.IUserDAO;
import com.nihaov.photograph.pojo.po.IMGPO;
import com.nihaov.photograph.pojo.po.UserPO;
import com.nihaov.photograph.pojo.vo.DataResult;
import com.nihaov.photograph.pojo.wx.WxUserInfo;
import com.nihaov.photograph.service.IUserService;
import com.nihaov.photograph.web.util.LoginUtils;
import com.nihaov.photograph.web.util.SecretPropertiesUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by nihao on 17/6/11.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IUserService userService;
    @Resource
    private IUserDAO userDAO;
    @Resource
    private LoginUtils loginUtils;

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
        StringBody appid = new StringBody(SecretPropertiesUtils.getAppId(), ContentType.create(
                "text/plain", Consts.UTF_8));
        StringBody secret = new StringBody(SecretPropertiesUtils.getAppSecret(), ContentType.create(
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
                if(wxUserInfo.getNickName() != null){
                    if(UTF8Utils.contains4BytesChar2(wxUserInfo.getNickName())){
                        wxUserInfo.setNickName(new String(UTF8Utils.remove4BytesUTF8Char(wxUserInfo.getNickName()),"UTF-8"));
                    }
                }
                UserPO userPO = new UserPO();
                userPO.setNickname(wxUserInfo.getNickName());
                userPO.setUnionId(openid);
                userPO.setHeadPic(wxUserInfo.getAvatarUrl());
                userPO.setGender(wxUserInfo.getGender());
                userPO.setCountry(wxUserInfo.getCountry());
                userPO.setCity(wxUserInfo.getCity());
                userPO.setProvince(wxUserInfo.getProvince());
                UserPO rePO = userService.auth(userPO);
                //登录记录
                loginUtils.login(request, rePO.getId());
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
            logger.error("auth error", e);
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
            //登录记录
            loginUtils.login(request, userPO.getId());
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

    @RequestMapping("/rmFavo")
    @ResponseBody
    public String rmFavo(HttpServletRequest request) {
        DataResult dataResult = new DataResult(200);
        String uid_ = request.getParameter("uid");
        String picId_ = request.getParameter("picId");
        userDAO.deleteFavo(Long.parseLong(uid_),Long.parseLong(picId_));
        return JSON.toJSONString(dataResult);
    }

    @RequestMapping("/ownFavoList")
    @ResponseBody
    public String ownFavoList(HttpServletRequest request) {
        DataResult dataResult = new DataResult();
        String uid_ = request.getParameter("uid");
        String page_ = request.getParameter("page");
        String pageCount_ = request.getParameter("pageCount");
        Long uid = Long.parseLong(uid_);
        Integer page = Integer.parseInt(page_);
        Integer pageCount = 9;
        if(!Strings.isNullOrEmpty(pageCount_)){
            pageCount = Integer.parseInt(pageCount_);
        }
        long count = userService.getFavoCount(uid);
        List<IMGPO> data = userService.getFavoByUidPagination(uid,page,pageCount);
        dataResult.setCode(200);
        Map<String,Object> result = new HashMap<>();
        result.put("count",count);
        result.put("list",data);
        result.put("pageSize",count/pageCount+(count%pageCount>0?1:0));
        dataResult.setResult(result);
        return JSON.toJSONString(dataResult, SerializerFeature.WriteDateUseDateFormat);
    }

    @RequestMapping("/suggestion")
    @ResponseBody
    public String suggestion(HttpServletRequest request) {
        DataResult dataResult = new DataResult(200);
        String uid_ = request.getParameter("uid");
        String content = request.getParameter("content");
        userDAO.insertSuggestion(uid_!=null?Long.parseLong(uid_):null,content);
        return JSON.toJSONString(dataResult);
    }
}
