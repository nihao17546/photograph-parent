package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.UserPO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by nihao on 17/6/11.
 */
public interface IUserDAO {
    int insert(UserPO userPO);
    UserPO selectByUserId(@Param("userId") String userId);
    UserPO selectByUserIdAndPassword(@Param("userId") String userId,@Param("password") String password);
}
