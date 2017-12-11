package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.ImagePO;
import com.nihaov.photograph.pojo.po.UserFavoPO;
import com.nihaov.photograph.pojo.po.UserPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by nihao on 17/6/11.
 */
public interface IUserDAO {
    int insert(UserPO userPO);
    UserPO selectByUserId(@Param("userId") String userId);
    UserPO selectByUserIdAndPassword(@Param("userId") String userId,@Param("password") String password);
    UserPO selectByUnionId(@Param("unionId") String unionId);
    int insertFavo(UserFavoPO userFavoPO);
    int deleteFavo(@Param("uid") Long uid,@Param("picId") Long picId);
    int insertSuggestion(@Param("uid") Long uid,@Param("content") String content);
    int updateByUnionId(UserPO userPO);
}
