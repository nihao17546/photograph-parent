package com.nihaov.photograph.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.nihaov.photograph.common.utils.BaseUtil;
import com.nihaov.photograph.dao.IIMageDAO;
import com.nihaov.photograph.pojo.po.ImagePO;
import com.nihaov.photograph.pojo.vo.ImageVO;
import com.nihaov.photograph.service.IBaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nihao on 17/4/22.
 */
@Service
public class BaseServiceImpl implements IBaseService {

    @Resource
    private IIMageDAO iMageDAO;

    @Override
    public List<ImageVO> getList(Integer size) {
        List<ImagePO> list = iMageDAO.selectRandom(size);
        return Lists.newArrayList(Lists.transform(list, new Function<ImagePO, ImageVO>() {
            @Override
            public ImageVO apply(ImagePO input) {
                return new ImageVO(input);
            }
        }));
    }
}
