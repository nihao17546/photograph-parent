package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.IMGPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by nihao on 17/6/15.
 */
public interface IMGDAO {
    List<IMGPO> selectRandom(@Param("limit") Integer limit);
    int insertError(@Param("imageId") Long imageId);
}
