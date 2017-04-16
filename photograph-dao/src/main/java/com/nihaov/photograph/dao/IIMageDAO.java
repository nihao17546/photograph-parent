package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.ImagePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by nihao on 17/4/12.
 */
public interface IIMageDAO {
    List<ImagePO> selectRandom(@Param("limit") Integer limit);
}
