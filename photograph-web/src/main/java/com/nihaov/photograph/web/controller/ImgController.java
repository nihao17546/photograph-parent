package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.nihaov.photograph.dao.IMGDAO;
import com.nihaov.photograph.pojo.po.IMGPO;
import com.nihaov.photograph.pojo.result.SearchResult;
import com.nihaov.photograph.pojo.vo.DataResult;
import com.nihaov.photograph.service.ISolrQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by nihao on 17/6/15.
 */
@Controller
public class ImgController {
    @Resource
    private IMGDAO imgdao;
    @Resource
    private ISolrQueryService solrQueryService;

    @RequestMapping("/random")
    @ResponseBody
    public String random(HttpServletRequest request){
        Integer limit = 60;
        String limit_ = request.getParameter("limit");
        if(!Strings.isNullOrEmpty(limit_))
            limit = Integer.parseInt(limit_);
        List<IMGPO> list = imgdao.selectRandom(limit);
        return JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/query/{page}/{rows}/{keyword}")
    @ResponseBody
    public String queryImage(@PathVariable("page") Integer page,
                             @PathVariable("rows") Integer rows,
                             @PathVariable("keyword") String keyword,
                             HttpServletRequest request){
        String sort = request.getParameter("sort");
        String asc = request.getParameter("asc");
        SearchResult searchResult = solrQueryService.query(keyword,page,rows,sort,asc);
        return JSON.toJSONString(searchResult,SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/error")
    @ResponseBody
    public String error(HttpServletRequest request){
        DataResult dataResult = new DataResult();
        String id_ = request.getParameter("picId");
        String errMsg = request.getParameter("errMsg");
        if(!Strings.isNullOrEmpty(id_)){
            imgdao.insertError(Long.parseLong(id_),errMsg);
        }
        dataResult.setCode(200);
        return JSON.toJSONString(dataResult);
    }
}
