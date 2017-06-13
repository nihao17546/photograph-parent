package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.nihaov.photograph.common.utils.DesUtil;
import com.nihaov.photograph.dao.IUserDAO;
import com.nihaov.photograph.pojo.po.UserPO;
import com.nihaov.photograph.pojo.vo.DataResult;
import com.nihaov.photograph.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        } catch (RuntimeException e){
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
        UserPO userPO = userDAO.selectByUserId(userId);
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
}
