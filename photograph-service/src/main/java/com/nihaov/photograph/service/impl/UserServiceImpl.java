package com.nihaov.photograph.service.impl;

import com.nihaov.photograph.common.utils.DesUtil;
import com.nihaov.photograph.dao.IUserDAO;
import com.nihaov.photograph.pojo.po.UserFavoPO;
import com.nihaov.photograph.pojo.po.UserPO;
import com.nihaov.photograph.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by nihao on 17/6/11.
 */
@Service
public class UserServiceImpl implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IUserDAO userDAO;

    private String userIdRegex = "^[0-9A-Za-z]{6,12}$";

    @Transactional
    @Override
    public void regist(UserPO userPO) {
        if(userPO.getUserId()==null||!userPO.getUserId().matches(userIdRegex)){
            throw new RuntimeException("账号须由数字或英文组成,长度6-12");
        }
        if(userPO.getNickname()==null){
            throw new RuntimeException("昵称不能为空");
        }
        if(userPO.getPassword()==null||userPO.getPassword().length()<6||userPO.getPassword().length()>20){
            throw new RuntimeException("密码长度要求6-20");
        }
        if(checkUserId(userPO.getUserId())){
            throw new RuntimeException("该账号已存在,请重新设置");
        }
        try {
            userPO.setPassword(DesUtil.encrypt(userPO.getPassword()));
        } catch (Exception e) {
            logger.error("密码加密失败,password is {}",userPO.getPassword(),e);
            throw new RuntimeException("抱歉,服务异常,请稍候再试");
        }
        userDAO.insert(userPO);
    }

    @Override
    public boolean checkUserId(String userId) {
        UserPO checkPO = userDAO.selectByUserId(userId);
        return checkPO!=null;
    }

    @Transactional
    @Override
    public UserPO auth(UserPO userPO) {
        if(userPO.getUnionId()==null){
            throw new RuntimeException("unionId获取失败");
        }
        UserPO checkPO = userDAO.selectByUnionId(userPO.getUnionId());
        if(checkPO==null){
            userDAO.insert(userPO);
        }
        userPO.setPassword(null);
        if(userPO.getId()==null){
            userPO.setId(checkPO.getId());
        }
        return userPO;
    }

    @Override
    public UserPO check(String checkKey) {
        if(checkKey.startsWith("pph_&")){
            return userDAO.selectByUnionId(checkKey.replaceFirst("pph_&",""));
        }
        else{
            return userDAO.selectByUserId(checkKey);
        }
    }

    @Override
    public int favo(Long uid, Long picId) {
        UserFavoPO userFavoPO = new UserFavoPO();
        userFavoPO.setUid(uid);
        userFavoPO.setPicId(picId);
        return userDAO.insertFavo(userFavoPO);
    }
}
