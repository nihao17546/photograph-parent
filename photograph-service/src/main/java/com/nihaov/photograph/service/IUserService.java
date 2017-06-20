package com.nihaov.photograph.service;

import com.nihaov.photograph.pojo.po.IMGPO;
import com.nihaov.photograph.pojo.po.UserPO;

import java.util.List;

/**
 * Created by nihao on 17/6/11.
 */
public interface IUserService {
    void regist(UserPO userPO);
    boolean checkUserId(String userId);
    UserPO auth(UserPO userPO);
    UserPO check(String checkKey);
    int favo(Long uid,Long picId);
    long getFavoCount(Long uid);
    List<IMGPO> getFavoByUidPagination(Long uid,Integer page,Integer pageCount);
}
