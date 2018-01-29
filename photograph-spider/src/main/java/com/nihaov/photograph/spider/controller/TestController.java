package com.nihaov.photograph.spider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by nihao on 18/1/25.
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "test";
    }
}
