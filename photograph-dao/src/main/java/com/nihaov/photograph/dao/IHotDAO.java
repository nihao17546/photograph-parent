package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.HotKeywordPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by nihao on 17/6/29.
 */
public interface IHotDAO {
    List<HotKeywordPO> select(@Param("limit") Integer limit);
}
