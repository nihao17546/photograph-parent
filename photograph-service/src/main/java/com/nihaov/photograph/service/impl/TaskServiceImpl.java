package com.nihaov.photograph.service.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.nihaov.photograph.dao.IHotDAO;
import com.nihaov.photograph.pojo.constant.BaseConstant;
import com.nihaov.photograph.pojo.po.HotKeywordPO;
import com.nihaov.photograph.service.ITaskService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nihao on 17/6/29.
 */
public class TaskServiceImpl implements ITaskService {

    @PostConstruct
    public void init() throws Exception {
        hotKeyword();
    }

    @Resource
    private IHotDAO hotDAO;

    @Override
    public void hotKeyword() {
        List<HotKeywordPO> hotKeywordPOList = hotDAO.select(5);
        BaseConstant.hot = Lists.newArrayList(Lists.transform(hotKeywordPOList, new Function<HotKeywordPO, String>() {
            @Override
            public String apply(HotKeywordPO input) {
                return input.getKeyword();
            }
        }));
    }
}
