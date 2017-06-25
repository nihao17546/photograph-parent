package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.nihaov.photograph.dao.IVisitDAO;
import com.nihaov.photograph.pojo.constant.BaseConstant;
import com.nihaov.photograph.pojo.result.SearchResult;
import com.nihaov.photograph.pojo.vo.ImageVO;
import com.nihaov.photograph.service.IBaseService;
import com.nihaov.photograph.service.ISearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nihao on 17/4/22.
 */
@Controller
public class BaseController {

    @Resource
    private IBaseService baseService;
    @Resource
    private ISearchService searchService;
    @Resource
    private IVisitDAO visitDAO;

    @RequestMapping("/s")
    public String index(Model model,HttpServletRequest request){
        String ip = getIpAddr(request);
        String userAgent = request.getHeader("user-agent");
        String host = request.getServerName();
        visitDAO.insert(ip,userAgent,host);
        model.addAttribute("compressPicPrefix",BaseConstant.compressPicPrefix);
        model.addAttribute("picPrefix",BaseConstant.picPrefix);
        return "s";
    }
    @RequestMapping("/find")
    @ResponseBody
    public String findAjax(HttpServletRequest request){
        String size_ = request.getParameter("size");
        Integer size = 60;
        if(!Strings.isNullOrEmpty(size_)){
            size = Integer.parseInt(size_);
        }
        List<ImageVO> list = baseService.getList(size);
        return JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
    }

    @RequestMapping("/query/image/tag/{page}/{rows}/{keyword}/{param}")
    @ResponseBody
    public String queryImageByTag(@PathVariable("page") Integer page,
                                  @PathVariable("rows") Integer rows,
                                  @PathVariable("keyword") String keyword){
        SearchResult searchResult = searchService.search("image_tag",keyword,page,rows);
        return JSON.toJSONString(searchResult,SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/query/image/{page}/{rows}/{keyword}")
    @ResponseBody
    public String queryImage(@PathVariable("page") Integer page,
                             @PathVariable("rows") Integer rows,
                             @PathVariable("keyword") String keyword){
        SearchResult searchResult = searchService.search(keyword,page,rows);
        return JSON.toJSONString(searchResult,SerializerFeature.WriteDateUseDateFormat);
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");
        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }
}
