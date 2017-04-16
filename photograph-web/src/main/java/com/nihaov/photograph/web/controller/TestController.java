package com.nihaov.photograph.web.controller;

import com.nihaov.photograph.dao.IIMageDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by nihao on 17/4/12.
 */
@Controller
public class TestController {
    @Value("#{configProperties['picPrefix']}")
    private String picPrefix;
    @Value("#{configProperties['staticUrl']}")
    private String staticUrl;
    @Resource
    private IIMageDAO iMageDAO;



    @RequestMapping("/search/{page}/{rows}/{keyword}")
    public String search(@PathVariable("keyword") String keyword,
                         @PathVariable("page") Integer page,
                         @PathVariable("rows") Integer rows,
                         Model model,
                         HttpServletResponse response){
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        model.addAttribute("rows",rows);
        Cookie cookie = new Cookie("1_&photopraph",System.currentTimeMillis()+"&"+ UUID.randomUUID().toString());
        cookie.setMaxAge(60);
        cookie.setPath("/");
        response.addCookie(cookie);
        model.addAttribute("staticUrl",staticUrl);
        return "search";
    }
}
