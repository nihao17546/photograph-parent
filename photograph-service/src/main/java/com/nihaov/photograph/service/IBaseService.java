package com.nihaov.photograph.service;

import com.nihaov.photograph.pojo.vo.ImageVO;

import java.util.List;

/**
 * Created by nihao on 17/4/22.
 */
public interface IBaseService {
    List<ImageVO> getList(Integer size);
}
