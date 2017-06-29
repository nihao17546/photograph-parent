package com.nihaov.photograph.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Strings;
import com.nihaov.photograph.common.utils.DesEncrypt;
import com.nihaov.photograph.common.utils.RedisUtil;
import com.nihaov.photograph.common.utils.SimpleDateUtil;
import com.nihaov.photograph.dao.IMGDAO;
import com.nihaov.photograph.pojo.enums.RedisKeyEnum;
import com.nihaov.photograph.pojo.po.IMGPO;
import com.nihaov.photograph.pojo.result.SearchResult;
import com.nihaov.photograph.pojo.vo.DataResult;
import com.nihaov.photograph.pojo.vo.IMGVO;
import com.nihaov.photograph.service.ISolrQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
    @Resource
    private RedisUtil redisUtil;

    private DesEncrypt desEncrypt = new DesEncrypt();

    /**
     * 网站用
     * @param request
     * @return
     */
    @RequestMapping("/img/rand")
    @ResponseBody
    public String rand(HttpServletRequest request){
        Integer limit = 60;
        String limit_ = request.getParameter("limit");
        if(!Strings.isNullOrEmpty(limit_))
            limit = Integer.parseInt(limit_);
        List<IMGPO> list = imgdao.selectRandom(limit);
        for(IMGPO imgpo:list){
            //对src加密
            imgpo.setSrc(desEncrypt.encrypt(imgpo.getSrc()));
        }
        return JSON.toJSONString(list, SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 网站用
     * @param page
     * @param rows
     * @param keyword
     * @param request
     * @return
     */
    @RequestMapping("/img/que/{page}/{rows}/{keyword}")
    @ResponseBody
    public String que(@PathVariable("page") Integer page,
                             @PathVariable("rows") Integer rows,
                             @PathVariable("keyword") String keyword,
                             HttpServletRequest request){
        String sort = request.getParameter("sort");
        String asc = request.getParameter("asc");
        String key = request.getParameter("k");
        SearchResult searchResult = solrQueryService.query(key,page,rows,sort,asc);
        if(searchResult.getData()!=null){
            for(Object obj:searchResult.getData()){
                IMGVO imgvo = (IMGVO) obj;
                //对src加密
                imgvo.setSrc(desEncrypt.encrypt(imgvo.getSrc()));
            }
        }
        String record_ = request.getParameter("record");
        if("true".equals(record_)){
            redisUtil.incr(SimpleDateUtil.shortFormat(new Date())+RedisKeyEnum.网站搜索关键字前缀.getValue()+key);
        }
        return JSON.toJSONString(searchResult,SerializerFeature.WriteDateUseDateFormat);
    }

    /**
     * 微信用
     * @param request
     * @return
     */
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
    /**
     * 微信用
     * @param request
     * @return
     */
    @RequestMapping("/query/{page}/{rows}/{keyword}")
    @ResponseBody
    public String queryImage(@PathVariable("page") Integer page,
                             @PathVariable("rows") Integer rows,
                             @PathVariable("keyword") String keyword,
                             HttpServletRequest request){
        String sort = request.getParameter("sort");
        String asc = request.getParameter("asc");
        SearchResult searchResult = solrQueryService.query(keyword,page,rows,sort,asc);
        if(page == 1){
            redisUtil.incr(SimpleDateUtil.shortFormat(new Date())+RedisKeyEnum.微信小程序搜索关键字前缀.getValue()+keyword);
        }
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
