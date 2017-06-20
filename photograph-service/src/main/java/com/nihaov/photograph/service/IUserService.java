package com.nihaov.photograph.service;

import com.nihaov.photograph.pojo.po.UserPO;

/**
 * Created by nihao on 17/6/11.
 */
public interface IUserService {
    void regist(UserPO userPO);
    boolean checkUserId(String userId);
    UserPO auth(UserPO userPO);
    UserPO check(String checkKey);
    int favo(Long uid,Long picId);
}
