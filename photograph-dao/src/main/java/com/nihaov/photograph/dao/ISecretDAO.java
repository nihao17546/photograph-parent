package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.SecretPO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Created by nihao on 17/9/5.
 */
public interface ISecretDAO {
    SecretPO selectByName(@Param("name") String name);
    @MapKey("name")
    Map<String,SecretPO> selectAll();
}
