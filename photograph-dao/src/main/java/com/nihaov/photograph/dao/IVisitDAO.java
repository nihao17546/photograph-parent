package com.nihaov.photograph.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by nihao on 17/6/22.
 */
public interface IVisitDAO {
    int insert(@Param("ip") String ip, @Param("userAgent") String userAgent,@Param("host") String host);
}
