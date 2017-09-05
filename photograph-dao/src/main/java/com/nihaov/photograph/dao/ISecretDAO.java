package com.nihaov.photograph.dao;

import com.nihaov.photograph.pojo.po.SecretPO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by nihao on 17/9/5.
 */
public interface ISecretDAO {
    SecretPO selectByName(@Param("name") String name);
}
