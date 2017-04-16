package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
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

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("compressPicPrefix",BaseConstant.compressPicPrefix);
        model.addAttribute("picPrefix",BaseConstant.picPrefix);
        return "index";
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
}
