package com.nihaov.photograph.web.controller;

import com.nihaov.photograph.common.utils.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by nihao on 17/6/29.
 */
@Controller
public class RedisController {
    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("/nihaosearchkey/{key}")
    public String redis(@PathVariable("key") String key,Model model){
        Map<String,String> map = redisUtil.mulGet(key);
        model.addAttribute("v",map);
        return "redis";
    }
}
